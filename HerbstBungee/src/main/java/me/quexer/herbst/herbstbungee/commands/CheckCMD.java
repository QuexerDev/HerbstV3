package me.quexer.herbst.herbstbungee.commands;

import javafx.scene.web.WebHistory;
import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Command;

import javax.naming.Name;
import java.util.UUID;

public class CheckCMD extends Command {

    private HerbstBungee plugin;

    public CheckCMD(HerbstBungee plugin) {
        super("check");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (plugin.getBackendManager().getPlayer(((ProxiedPlayer) sender).getUniqueId().toString()).getGroup().getLevelID() > 5) {
            if (args.length == 1) {
                UUIDFetcher.getUUID(args[0], uuid -> {
                    UUIDFetcher.getName(uuid, name -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }
                        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(uuid.toString());
                        sender.sendMessage(plugin.getPrefix());
                        sender.sendMessage(plugin.getPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
                        sender.sendMessage(plugin.getPrefix() + "§7Name§8: §e" + backendPlayer.getGroup().getColor() + name);
                        sender.sendMessage(plugin.getPrefix() + "§7BanPoints§8: §e" + (backendPlayer.getBanPlayer().getBanPoints() + backendPlayer.getMutePlayer().getBanPoints()));
                        sender.sendMessage(plugin.getPrefix() + "§7Rang§8: " + backendPlayer.getGroup().getFullName());
                        sender.sendMessage(plugin.getPrefix());
                        sender.sendMessage(plugin.getPrefix() + "§7Gebannt§8: " + (backendPlayer.getBanPlayer().isPunished() ? "§aJa" : "§cNein"));
                        if (backendPlayer.getBanPlayer().isPunished()) {
                            sender.sendMessage(plugin.getPrefix() + "§7Grund§8: §e" + backendPlayer.getBanPlayer().getReason());
                            sender.sendMessage(plugin.getPrefix() + "§7Gebannt von§8: §e" + plugin.getBackendManager().getPlayer(backendPlayer.getBanPlayer().getPunished_from()).getGroup().getPrefix() + UUIDFetcher.getName(UUID.fromString(backendPlayer.getBanPlayer().getPunished_from())));
                            sender.sendMessage(plugin.getPrefix() + "§7Gebannt bis§8: §e" + plugin.getDate(backendPlayer.getBanPlayer().getEnd()));
                        }
                        sender.sendMessage(plugin.getPrefix() + "§7BanHistory§8:");
                        if (backendPlayer.getBanPlayer().getHistory().size() == 0) {
                            sender.sendMessage(plugin.getPrefix() + "§cDieser Spieler wurde noch nie gebannt!");
                        } else {
                            backendPlayer.getBanPlayer().getHistory().forEach(history -> {
                                sender.sendMessage(plugin.getPrefix() + "§8- §e" + history);
                            });
                        }
                        sender.sendMessage(plugin.getPrefix());
                        sender.sendMessage(plugin.getPrefix() + "§7Gemutet§8: " + (backendPlayer.getMutePlayer().isPunished() ? "§aJa" : "§cNein"));
                        if (backendPlayer.getMutePlayer().isPunished()) {
                            sender.sendMessage(plugin.getPrefix() + "§7Grund§8: §e" + backendPlayer.getMutePlayer().getReason());
                            sender.sendMessage(plugin.getPrefix() + "§7Gemutet von§8: §e" + plugin.getBackendManager().getPlayer(backendPlayer.getMutePlayer().getPunished_from()).getGroup().getPrefix() + UUIDFetcher.getName(UUID.fromString(backendPlayer.getMutePlayer().getPunished_from())));
                            sender.sendMessage(plugin.getPrefix() + "§7Gemutet bis§8: §e" + plugin.getDate(backendPlayer.getMutePlayer().getEnd()));
                        }
                        sender.sendMessage(plugin.getPrefix() + "§7MuteHistory§8:");
                        if (backendPlayer.getMutePlayer().getHistory().size() == 0) {
                            sender.sendMessage(plugin.getPrefix() + "§cDieser Spieler wurde noch nie gemutet!");
                        } else {
                            backendPlayer.getMutePlayer().getHistory().forEach(history -> {
                                sender.sendMessage(plugin.getPrefix() + "§8- §e" + history);
                            });
                        }
                        sender.sendMessage(plugin.getPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
                        sender.sendMessage(plugin.getPrefix());
                    });
                });
            } else
                sender.sendMessage(plugin.getPrefix() + "§7Benutze§8: §c/check <Name>");
        }
    }
}
