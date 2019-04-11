package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FriendCMD extends Command {

    private HerbstBungee plugin;

    public FriendCMD(HerbstBungee plugin) {
        super("friend");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //friend add [Name]
        //friend deny [Name]
        //friend accept [Name]
        //friend toggle [Name]
        //friend block [Name]
        //friend release [Name]
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {

                case "add": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().sendRequest(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "accept": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().acceptFriend(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "deny": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().denyFriend(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "remove": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().removeFriend(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "block": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().blockPlayer(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "release": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().releasePlayer(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                case "toggle": {
                    plugin.getFriendManager().changeSetting(player.getUniqueId().toString(), args[1]);
                    break;
                }
                case "jump": {
                    UUIDFetcher.getUUID(args[1], uuid -> {
                        if (uuid == null) {
                            sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                            return;
                        }

                        plugin.getFriendManager().jump(player.getUniqueId().toString(), uuid.toString());

                    });
                    break;
                }

                default: {
                    help(sender);
                    break;
                }
            }

        } else
                help(sender);



    }

    private void help(CommandSender sender) {
        sender.sendMessage(plugin.getFriendPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend add [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend accept [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend deny [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend toggle [MSG|SERVER|REQUEST|JUMP]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend block [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend release [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§e/friend remove [Name]");
        sender.sendMessage(plugin.getFriendPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }
}
