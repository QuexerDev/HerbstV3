package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnMuteCMD extends Command {
    private HerbstBungee plugin;

    public UnMuteCMD(HerbstBungee plugin) {
        super("unmute");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (plugin.getBackendManager().getPlayer(((ProxiedPlayer) sender).getUniqueId().toString()).getGroup().getLevelID() > 8) {
                UUIDFetcher.getUUID(args[0], uuid -> {
                    if (uuid == null) {
                        sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                        return;
                    }
                    plugin.getMuteManager().unMute(uuid.toString(), ((ProxiedPlayer) sender).getUniqueId().toString(), args[1]);
                });
            }
        } else
            sender.sendMessage(plugin.getPrefix() + "§7Benutze§8: §c/unmute <Player> <Reason>");

    }
}
