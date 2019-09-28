package me.quexer.herbst.herbstplugin.manager;

import me.quexer.api.quexerapi.api.NickAPI;
import me.quexer.herbst.herbstplugin.HerbstPlugin;
import me.quexer.herbst.herbstplugin.obj.BackendGroup;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.herbst.herbstplugin.obj.Data;
import me.quexer.herbst.herbstplugin.obj.LobbyPlayer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.SocketHandler;
import java.util.stream.Collectors;

public class BackendManager {


    private HerbstPlugin plugin;
    private List<BackendGroup> groups;

    public BackendManager(HerbstPlugin plugin) {
        this.plugin = plugin;
        initGroups();
    }

    private void initGroups() {
        groups = new ArrayList<>();
        groups.add(new BackendGroup("§4Administrator", "§4Admin §8| §7", "§4", 13, 1));
        groups.add(new BackendGroup("§bSrDeveloper", "§bSrDev §8| §7", "§b", 12, 2));
        groups.add(new BackendGroup("§cSrModerator", "§cSrMod §8| §7", "§c", 11, 3));
        groups.add(new BackendGroup("§bDeveloper", "§bDev §8| §7", "§b", 10, 4));
        groups.add(new BackendGroup("§cModerator", "§cMod §8| §7", "§c", 9, 5));
        groups.add(new BackendGroup("§2Builder", "§2Builder §8| §7", "§2", 8, 6));
        groups.add(new BackendGroup("§bContent", "§bContent §8| §7", "§b", 7, 7));
        groups.add(new BackendGroup("§9Supporter", "§9Sup §8| §7", "§9", 6, 8));
        groups.add(new BackendGroup("§5YouTuber", "§5YouTube §8| §7", "§5", 5, 9));
        groups.add(new BackendGroup("§3Master", "§3Master §8| §7", "§3", 4, 10));
        groups.add(new BackendGroup("§eVIP", "§eVIP §7| §e", "§7", 3, 11));
        groups.add(new BackendGroup("§6Premium", "§ePremium §7| §7", "§6", 2, 12));
        groups.add(new BackendGroup("§aSpieler", "§aSpieler §8| §7", "§a", 1, 13));

    }

    public BackendGroup getLowestGroup() {
        return groups.stream().filter(backendGroup -> backendGroup.getLevelID() == 1).collect(Collectors.toList()).get(0);
    }

    public BackendPlayer getPlayer(String uuid) {
        if (Bukkit.getPlayer(UUID.fromString(uuid)) != null && Bukkit.getPlayer(UUID.fromString(uuid)).hasMetadata("backendplayer")) {
            System.out.println("Get From META");
            return (BackendPlayer) Bukkit.getPlayer(UUID.fromString(uuid)).getMetadata("backendplayer").get(0).value();
        } else {
            try {
                CompletableFuture<BackendPlayer> completableFuture = new CompletableFuture<>();
                plugin.getBackendPlayerManager().getFromDB(UUID.fromString(uuid), backendPlayer -> {
                    completableFuture.complete(backendPlayer);
                    System.out.println("Get From Database");
                });
                return completableFuture.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String numberFormat(Number number) {
        return new DecimalFormat("###,###,###,###,###,###").format(number);
    }

    public void savePlayer(BackendPlayer backendPlayer) {
        plugin.getBackendPlayerManager().saveToDB(backendPlayer);
    }

    public void nick(Player player) {
        BackendPlayer backendPlayer = getPlayer(player.getUniqueId().toString());

        if (backendPlayer.getGroup().hasPermission(3)) {
            NickAPI.setRandomNick(player);
            plugin.getQuexerAPI().setMetadata(player, "backendplayer", backendPlayer);
        }
    }

    public void unnick(Player player) {
        if (NickAPI.hasNick(player)) {
            NickAPI.removeNick(player);
        }
    }


    public void addPLayerToGroup(String uuid, BackendGroup group) {
        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(uuid);
        removePlayerFromGroup(uuid);
        backendPlayer.setGroup(group);

        UUID id = UUID.fromString(uuid);
        plugin.getRabbitMQ().publish("kick;"+group.getLevelID()+";"+id.toString(), "herbst-bungee");

    }


    public void removePlayerFromGroup(String uuid) {
        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(uuid);
        backendPlayer.setGroup(plugin.getBackendManager().getLowestGroup());
    }

    public List<BackendGroup> getGroups() {
        return groups;
    }

    public Document lobbyPlayerToDocument(LobbyPlayer lobbyPlayer) {
        return new Document("extraTypes", lobbyPlayer.getExtraTypes())
                .append("dailyReward", lobbyPlayer.getDailyReward());
    }

    public Document dataToDocument(Data data) {

        return new Document("coins", data.getCoins())
                .append("keys", data.getKeys())
                .append("cpr", data.getCpr())
                .append("elo", data.getElo())
                .append("nick", data.isNick())
                .append("lobbyPlayer", lobbyPlayerToDocument(data.getLobbyPlayer()));

    }

}


