package me.quexer.verbstv3.lobbysystem.manager;

import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

public class ScoreboardManager {

    private org.bukkit.scoreboard.Scoreboard board;
    private Objective obj;
    private LobbySystem plugin;
    private Scoreboard sb;
    //private HashMap<Group, Team> groupTeamHashMap = new HashMap<>();


    public ScoreboardManager(LobbySystem plugin) {
        this.plugin = plugin;
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("aaaaa", "bbbbb");

       /* for (int i = 0; i < Group.values().length; i++) {
            Group group = Group.values()[i];
            Team team = board.registerNewTeam("" + group.getTabID() + group.toString());
            System.out.println("" + group.getTabID() + group.toString());
            team.setPrefix(group.getPrefix());
            groupTeamHashMap.put(group, team);
        }
        */
    }

   /* public void setTablist(Player player) {
        BackendPlayer user = (BackendPlayer) player.getMetadata("user").get(0).value();
        groupTeamHashMap.get(user.getGroup()).addPlayer(player);

        player.setPlayerListName(user.getGroup().getPrefix() + player.getName());
        player.setDisplayName(user.getGroup().getPrefix() + player.getName());

        Bukkit.getOnlinePlayers().forEach(o -> {
            o.setScoreboard(board);
        });
    }
    */

    public void setBoard(Player player) {
        sb = new Scoreboard();

        ScoreboardObjective obj = sb.registerObjective("§8» §eHerbstV3", IScoreboardCriteria.b);
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        BackendPlayer user = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());

        ScoreboardScore a = new ScoreboardScore(sb, obj, "§a§8§m-----------------");
        ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§7Rang:");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, " §8» " + user.getGroup().getFullName());
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§d");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§7Coins:");
        ScoreboardScore a5 = new ScoreboardScore(sb, obj, " §8» " + "§e"+ plugin.getBackendManager().numberFormat(user.getData().getCoins()));
        ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§0");
        ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§7Keys:");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, " §8» §a"+plugin.getBackendManager().numberFormat(user.getData().getKeys()));
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, "§4");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§7Website:");
        ScoreboardScore a12 = new ScoreboardScore(sb, obj, " §8» §6Herbst.net");
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, "§7§8§m-----------------");


        a.setScore(12);
        a1.setScore(11);
        a2.setScore(10);
        a3.setScore(9);
        a4.setScore(8);
        a5.setScore(7);
        a6.setScore(6);
        a7.setScore(5);
        a8.setScore(4);
        a10.setScore(3);
        a11.setScore(2);
        a12.setScore(1);
        a9.setScore(0);



        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa = new PacketPlayOutScoreboardScore(a);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
        sendPacket(player, removePacket);
        sendPacket(player, createpacket);
        sendPacket(player, display);
        sendPacket(player, pa);
        sendPacket(player, pa1);
        sendPacket(player, pa2);
        sendPacket(player, pa3);
        sendPacket(player, pa4);
        sendPacket(player, pa5);
        sendPacket(player, pa6);
        sendPacket(player, pa7);
        sendPacket(player, pa8);
        sendPacket(player, pa9);
        sendPacket(player, pa10);
        sendPacket(player, pa11);
        sendPacket(player, pa12);

        plugin.getQuexerAPI().setMetadata(player, "scoreboard",sb);
    }

    public  void sendPacket(Player p, Packet packet) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    public  void updateScoreboard(Player player) {
        Scoreboard sb = (Scoreboard) player.getMetadata("scoreboard").get(0).value();


        ScoreboardObjective obj = sb.getObjective("§8» §eHerbstV3");
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

        BackendPlayer user = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());

        ScoreboardScore a = new ScoreboardScore(sb, obj, "§a§8§m-----------------");
        ScoreboardScore a1 = new ScoreboardScore(sb, obj, "§7Rang:");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, " §8» " + user.getGroup().getFullName());
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, "§d");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§7Coins:");
        ScoreboardScore a5 = new ScoreboardScore(sb, obj, " §8» " + "§e"+ plugin.getBackendManager().numberFormat(user.getData().getCoins()));
        ScoreboardScore a6 = new ScoreboardScore(sb, obj, "§0");
        ScoreboardScore a7 = new ScoreboardScore(sb, obj, "§7Keys:");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, " §8» §a"+plugin.getBackendManager().numberFormat(user.getData().getKeys()));
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, "§4");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§7Website:");
        ScoreboardScore a12 = new ScoreboardScore(sb, obj, " §8» §6Herbst.net");
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, "§7§8§m-----------------");

        a.setScore(12);
        a1.setScore(11);
        a2.setScore(10);
        a3.setScore(9);
        a4.setScore(8);
        a5.setScore(7);
        a6.setScore(6);
        a7.setScore(5);
        a8.setScore(4);
        a10.setScore(3);
        a11.setScore(2);
        a12.setScore(1);
        a9.setScore(0);



        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa = new PacketPlayOutScoreboardScore(a);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a1);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(a5);
        PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(a6);
        PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(a7);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
        sendPacket(player, removePacket);
        sendPacket(player, createpacket);
        sendPacket(player, display);
        sendPacket(player, pa);
        sendPacket(player, pa1);
        sendPacket(player, pa2);
        sendPacket(player, pa3);
        sendPacket(player, pa4);
        sendPacket(player, pa5);
        sendPacket(player, pa6);
        sendPacket(player, pa7);
        sendPacket(player, pa8);
        sendPacket(player, pa9);
        sendPacket(player, pa10);
        sendPacket(player, pa11);
        sendPacket(player, pa12);

        plugin.getQuexerAPI().setMetadata(player, "scoreboard",sb);
    }

}
