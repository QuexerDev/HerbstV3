package me.quexer.herbst.herbstbungee.manager;



import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.obj.BackendGroup;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import me.quexer.herbst.herbstbungee.obj.Report;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class BackendManager {


    private HerbstBungee plugin;
    private List<BackendGroup> groups;

    private HashMap<String, BackendPlayer> backendPlayerCache;
    private HashMap<String, Report> reportPlayerCache;

    public BackendManager(HerbstBungee plugin) {
        this.plugin = plugin;

        backendPlayerCache = new HashMap<>();
        reportPlayerCache = new HashMap<>();

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
        if (backendPlayerCache.containsKey(uuid)) {
            System.out.println("Get From META");
            return backendPlayerCache.get(uuid);
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


    public BackendPlayer savePlayer(BackendPlayer backendPlayer) {
        CompletableFuture<BackendPlayer> completableFuture = new CompletableFuture<>();
        completableFuture.complete(plugin.getBackendPlayerManager().saveToDB(backendPlayer));
        backendPlayerCache.put(backendPlayer.getUuid().toLowerCase(), backendPlayer);
        try {
            return completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void addPLayerToGroup(String uuid, BackendGroup group) {
        BackendPlayer backendPlayer = getPlayer(uuid);
        backendPlayer.setGroup(group);


        UUID id = UUID.fromString(uuid);
        if (plugin.getProxy().getPlayer(id) != null) {
            plugin.getProxy().getPlayer(id).disconnect(
                    "§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n" +
                            "§7Du hast die Gruppe " + group.getFullName() + " §7erhalten!\n" +
                            "§7Bitte betrete den Server erneut§8.\n" +
                            "§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        }
        savePlayer(backendPlayer);


    }


    public void removePlayerFromGroup(String uuid) {
        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(uuid);
        backendPlayer.setGroup(plugin.getBackendManager().getLowestGroup());
    }

    public List<BackendGroup> getGroups() {
        return groups;
    }

    public HashMap<String, BackendPlayer> getBackendPlayerCache() {
        return backendPlayerCache;
    }

    public HashMap<String, Report> getReportPlayerCache() {
        return reportPlayerCache;
    }
}


