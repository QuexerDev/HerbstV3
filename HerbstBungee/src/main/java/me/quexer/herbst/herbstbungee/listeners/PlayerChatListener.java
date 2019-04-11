package me.quexer.herbst.herbstbungee.listeners;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    private HerbstBungee plugin;

    public PlayerChatListener(HerbstBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(ChatEvent event) {
    /*
    if (plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) != null) {
                ProxiedPlayer proxiedPlayer = plugin.getProxy().getPlayer(UUID.fromString(targetUUID));
                proxiedPlayer.sendMessage(plugin.getPrefix());
                proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Du wurdest von "+sender.getGroup().getPrefix()+senderName+" §7gemutet§8!");
                proxiedPlayer.sendMessage(plugin.getPrefix());
                proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Gemutet bis§8: §e"+plugin.getDate(end));
                proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Grund§8: §e"+reason);
                proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Mute-Points§8: §e"+mute.getBanPoints());
                proxiedPlayer.sendMessage(plugin.getPrefix());
            }
     */
        if (!event.getMessage().startsWith("/")) {
            if (plugin.getBackendManager().getPlayer(((ProxiedPlayer) event.getSender()).getUniqueId().toString()).getMutePlayer().isPunished()) {
                BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(((ProxiedPlayer) event.getSender()).getUniqueId().toString());
                if (System.currentTimeMillis() < backendPlayer.getMutePlayer().getEnd() || backendPlayer.getMutePlayer().getEnd() == -1) {
                    event.setCancelled(true);
                    ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
                    proxiedPlayer.sendMessage(plugin.getPrefix());
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Du bist gemutet§8!");
                    proxiedPlayer.sendMessage(plugin.getPrefix());
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Gemutet von§8: §e"+plugin.getBackendManager().getPlayer(backendPlayer.getMutePlayer().getPunished_from()).getGroup().getPrefix()+ UUIDFetcher.getName(UUID.fromString(backendPlayer.getMutePlayer().getPunished_from())));
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Gemutet bis§8: §e"+plugin.getDate(backendPlayer.getMutePlayer().getEnd()));
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Grund§8: §e"+backendPlayer.getMutePlayer().getReason());
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§7Mute-Points§8: §e"+backendPlayer.getMutePlayer().getBanPoints());
                    proxiedPlayer.sendMessage(plugin.getPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
                    proxiedPlayer.sendMessage(plugin.getPrefix());
                } else
                    plugin.getMuteManager().unMuteConsole(((ProxiedPlayer) event.getSender()).getUniqueId().toString());
            }
        }
    }


}
