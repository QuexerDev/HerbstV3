package me.quexer.herbst.herbstapi.listeners;

import me.quexer.api.quexerapi.event.EventManager;
import me.quexer.api.quexerapi.event.PlayerNickEvent;
import me.quexer.api.quexerapi.event.PlayerRemoveNickEvent;
import me.quexer.api.quexerapi.misc.AsyncTask;
import me.quexer.herbst.herbstapi.HerbstAPI;
import me.quexer.herbst.herbstplugin.HerbstPlugin;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.help.HelpTopic;

public class PlayerListeners {

    private HerbstAPI plugin;

    private EventManager.EventListener<PlayerJoinEvent> joinEvent;
    private EventManager.EventListener<PlayerQuitEvent> quitEvent;
    private EventManager.EventListener<AsyncPlayerChatEvent> chatEvent;
    private EventManager.EventListener<PlayerNickEvent> nickEvent;
    private EventManager.EventListener<PlayerRemoveNickEvent> removeNickEvent;
    private EventManager.EventListener<PlayerCommandPreprocessEvent> commandEvent;

    public PlayerListeners(HerbstAPI plugin) {
        this.plugin = plugin;
        initListeners();
    }

    private void initListeners() {
        joinEvent = event -> {
            event.setJoinMessage(null);
            Bukkit.getOnlinePlayers().forEach(o -> {
                o.hidePlayer(event.getPlayer());
            });
            BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString());
            System.out.println(backendPlayer);
            if (plugin.isTabOnThisServer())
                plugin.getTablistManager().setTablist(event.getPlayer(), backendPlayer);
            if (plugin.isNickOnThisServer() && backendPlayer.getGroup().getLevelID() >= 2) {
                if (backendPlayer.getData().isNick()) {
                    plugin.getBackendManager().nick(event.getPlayer());
                }
            }
            Bukkit.getOnlinePlayers().forEach(o -> {
                o.showPlayer(event.getPlayer());
            });

        };

        quitEvent = event -> {
            event.setQuitMessage(null);
            BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString());
            plugin.getBackendPlayerManager().saveToDB(backendPlayer);
            plugin.getQuexerAPI().removeMetadata(event.getPlayer(), "backendplayer");

        };

        chatEvent = event -> {
            event.setFormat(event.getPlayer().getDisplayName() + " §8➜ §7" + event.getMessage());
            String msg = event.getMessage().replace("%", "%%");
            event.setMessage(msg);
            if (!event.isCancelled()) {
                if(msg.startsWith("/")) {
                    String cmd = event.getMessage().split(" ")[0];
                    HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
                    if (topic == null) {
                        event.getPlayer().sendMessage(plugin.getPrefix() + "§7Dieser Befehl existiert nicht, versuche §e/help §7für eine Übersicht");
                        event.setCancelled(true);
                    }
                }
            }

        };

        removeNickEvent = event -> {
            new AsyncTask(() -> {
                event.getPlayer().sendMessage("");
                event.getPlayer().sendMessage("§5NICK §8▎ §7Dein §eNickname §7wurde entfernt§8!");
                event.getPlayer().sendMessage("");
                if (plugin.isTabOnThisServer())
                    plugin.getTablistManager().setTablist(event.getPlayer(), plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()));
            });
        };
        nickEvent = event -> {
            new AsyncTask(() -> {
                event.getPlayer().sendMessage("");
                event.getPlayer().sendMessage("§5NICK §8▎ §7Dein neuer §eNickname §7lautet§8: §6" + event.getNick());
                // event.getPlayer().sendMessage("§7➜ §5§lNick §f▎  §7Dein neuer §e§lNickname §7lautet§8: §6"+event.getNick());
                event.getPlayer().sendMessage("");
                if (plugin.isTabOnThisServer())
                    plugin.getTablistManager().setTablist(event.getPlayer(), plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()));
            });
        };


        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerJoinEvent.class, joinEvent);
        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerQuitEvent.class, quitEvent);
        plugin.getQuexerAPI().getEventManager().registerEvent(AsyncPlayerChatEvent.class, chatEvent);
        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerNickEvent.class, nickEvent);
        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerRemoveNickEvent.class, removeNickEvent);
    }


}
