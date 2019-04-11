package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCMD extends Command {

    private HerbstBungee plugin;

    public ReportCMD(HerbstBungee plugin) {
        super("report");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 2) {
            switch (args[1].toLowerCase()) {

                case "accept": {
                    BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());
                    UUIDFetcher.getUUID(args[0], uuid -> {
                        if (backendPlayer.getGroup().getLevelID() > 8) {
                            if (uuid == null) {
                                sender.sendMessage(plugin.getReportPrefix() + "§cDieser Spieler existiert nicht!");
                                return;
                            }

                            plugin.getReportManager().accept(plugin.getBackendManager().getReportPlayerCache().get(uuid.toString()), player.getUniqueId().toString());
                        } else
                            plugin.getReportManager().report(player.getUniqueId().toString(), uuid.toString(), args[1]);
                    });

                    break;
                }
                default: {
                    UUIDFetcher.getUUID(args[0], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getReportPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }
                        plugin.getReportManager().report(player.getUniqueId().toString(), uuid.toString(), args[1]);

                    });

                    break;
                }
            }

        } else
            help(sender);
    }

    private void help(CommandSender sender) {
        sender.sendMessage(plugin.getReportPrefix() + "§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        sender.sendMessage(plugin.getReportPrefix() + "§e/report [Name] [Grund]");
        sender.sendMessage(plugin.getReportPrefix() + "§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }
}
