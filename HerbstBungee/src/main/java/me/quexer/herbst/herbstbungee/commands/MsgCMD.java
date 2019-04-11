package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCMD extends Command {

    private HerbstBungee plugin;

    public MsgCMD(HerbstBungee plugin) {
        super("msg");
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            help(sender);
            return;
        }

        UUIDFetcher.getUUID(args[0], uuid -> {
            if (uuid == null) {
                sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                return;
            }

            String msg = "";
            for (int i = 1; i < args.length; i++) {
                msg = msg+args[i]+" ";
            }
            plugin.getFriendManager().msg(((ProxiedPlayer) sender).getUniqueId().toString(), uuid.toString(), msg);

        });

    }

    private void help(CommandSender sender) {
        sender.sendMessage(plugin.getFriendPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/msg [Name] [Nachricht]");
        sender.sendMessage(plugin.getFriendPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }
}
