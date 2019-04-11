package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCMD extends Command {

    private HerbstBungee plugin;

    public BanCMD(HerbstBungee plugin) {
        super("ban");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //pun [Player] [Reason]
        if (plugin.getBackendManager().getPlayer(((ProxiedPlayer) sender).getUniqueId().toString()).getGroup().getLevelID() > 8) {
            if (args.length == 2) {


                String id = args[1];
                
                plugin.getProxy().getScheduler().runAsync(plugin.getInstance(), () -> {
                    String targetUUID = UUIDFetcher.getUUID(args[0]).toString();
                    if (targetUUID == null) {
                        sender.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
                        return;
                    }
                    switch (id) {
                        case "1":
                                plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Hacking", 24*14, 3);
                            break;

                        case "2":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Trolling", 24*3, 2);
                            break;
                        case "3":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Teaming", 24*1, 1);
                            break;

                        case "4":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Bugusing", 24*3, 2);
                            break;

                        case "5":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Skin", 2, 1);
                            break;

                        case "6":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Name", 2, 1);
                            break;

                        case "7":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Reportausnutzung", 24*2, 1);
                            break;

                        case "8":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Banumgehung", -1, 10);
                            break;

                        case "9":
                            plugin.getBanManager().ban(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Hausverbot", -1, 10);
                            break;
                            
                            
                        case "10":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Beleidigung", 24*3, 2);
                            break;

                        case "11":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Rassismus", 24*7, 3);
                            break;

                        case "12":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Werbung", 24*1, 1);
                            break;

                        case "13":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Spam", 12, 1);
                            break;

                        case "14":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Provokation", 12, 2);
                            break;

                        case "15":
                            plugin.getMuteManager().mute(targetUUID, ((ProxiedPlayer) sender).getUniqueId().toString(), "Schweigepflicht", -1, 10);
                            break;

                        default:
                            sender.sendMessage(plugin.getPrefix() + "§7Benutze§8: §c/ban <Spieler> <Grund>");

                            TextComponent text = new TextComponent();
                            text.setText(plugin.getPrefix() + "§7Für eine Liste mit Gründen §8[" + ChatColor.GREEN + "Klicke hier§8]");
                            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reasons"));
                            sender.sendMessage(text);
                            break;
                    }
                });
            } else {
                sender.sendMessage(plugin.getPrefix() + "§7Benutze§8: §c/ban <Spieler> <Grund>");

                TextComponent text = new TextComponent();
                text.setText(plugin.getPrefix() + "§7Für eine Liste mit Gründen §8[" + ChatColor.GREEN + "Klicke hier§8]");
                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reasons"));
                sender.sendMessage(text);

            }
        }


    }

}
