package me.quexer.herbst.herbstapi.utils;

import me.quexer.api.quexerapi.api.NickAPI;
import me.quexer.herbst.herbstapi.HerbstAPI;
import me.quexer.herbst.herbstplugin.obj.BackendGroup;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TablistManager {

    private HerbstAPI plugin;

    private org.bukkit.scoreboard.Scoreboard board;
    private Objective obj;
    private net.minecraft.server.v1_8_R3.Scoreboard sb;
    private HashMap<Integer, Team> groupTeamHashMap = new HashMap<>();

    public TablistManager(HerbstAPI plugin) {
        this.plugin = plugin;

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("aaaaa", "bbbbb");

        for (int i = 0; i < plugin.getBackendManager().getGroups().size(); i++) {
            BackendGroup group = plugin.getBackendManager().getGroups().get(i);
            Team team = board.registerNewTeam("" + group.getTabID() + ChatColor.stripColor(group.getPrefix().split(" ")[0]).toUpperCase().toString());
            System.out.println(team.getName());
            System.out.println("" + group.getTabID() + group.toString());
            team.setPrefix(group.getPrefix());
            groupTeamHashMap.put(group.getLevelID(), team);
        }
    }

    public void setTablist(Player player, BackendPlayer backendPlayer) {
        if(!NickAPI.hasNick(player)) {
            groupTeamHashMap.get(backendPlayer.getGroup().getLevelID()).addPlayer(player);

            player.setPlayerListName(backendPlayer.getGroup().getPrefix() + player.getName());
            player.setDisplayName(backendPlayer.getGroup().getPrefix() + player.getName());
        } else {
            groupTeamHashMap.get(plugin.getBackendManager().getLowestGroup().getLevelID()).addPlayer(player);

            player.setPlayerListName(plugin.getBackendManager().getLowestGroup().getPrefix() + player.getName());
            player.setDisplayName(plugin.getBackendManager().getLowestGroup().getPrefix() + player.getName());
        }

        Bukkit.getOnlinePlayers().forEach(o -> {
            o.setScoreboard(board);
        });
    }

}
