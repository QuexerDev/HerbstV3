package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendGroup;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupCMD extends Command {

    private HerbstBungee plugin;

    public GroupCMD(HerbstBungee plugin) {
        super("group");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean isPlayer = (sender instanceof ProxiedPlayer);
        boolean allow = false;
        if (isPlayer && plugin.getBackendManager().getPlayer(((ProxiedPlayer) sender).getUniqueId().toString()).getGroup().getLevelID() >= 12) {
            allow = true;
        } else if (!isPlayer && sender.hasPermission("admin")) {
            allow = true;
        } else {
            allow = false;
        }
        if (allow) {
            switch (args.length) {
                case 1: {
                    if (args[0].equalsIgnoreCase("list")) {
                        sender.sendMessage("§8§m-------------------------");
                        sender.sendMessage("§7Spielergruppen:");
                        plugin.getBackendManager().getGroups().forEach(backendGroup -> {
                            sender.sendMessage("§7- " + backendGroup.getFullName() + " §8/ §e" + backendGroup.getLevelID());
                        });
                        sender.sendMessage("§8§m-------------------------");
                    } else
                        help(sender);

                    break;
                }
                case 2: {
                    if (args[0].equalsIgnoreCase("info")) {
                        UUIDFetcher.getUUID(args[1], uuid -> {
                            if (uuid != null) {
                                BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(UUID.fromString(uuid.toString()).toString());
                                sender.sendMessage(plugin.getPrefix() + "§7Der Spieler " + backendPlayer.getGroup().getColor() + args[1] + " §7hat die Gruppe§8: " + backendPlayer.getGroup().getFullName());
                            } else {
                                sender.sendMessage(plugin.getPrefix() + "§cDieser Spieler existiert nicht!");
                            }
                        });
                    } else
                        help(sender);
                    break;
                }
                case 3: {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (Integer.valueOf(args[2]) <= plugin.getBackendManager().getGroups().size()) {
                            BackendGroup group = plugin.getBackendManager().getGroups().stream().filter(backendGroup -> backendGroup.getLevelID() == Integer.valueOf(args[2])).collect(Collectors.toList()).get(0);
                            UUIDFetcher.getUUID(args[1], uuid -> {
                                if (uuid != null) {
                                    BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(UUID.fromString(uuid.toString()).toString());
                                    plugin.getBackendManager().addPLayerToGroup(UUID.fromString(uuid.toString()).toString(), group);
                                    sender.sendMessage(plugin.getPrefix() + "§7Der Spieler " + group.getColor() + args[1] + " §7hat die Gruppe§8: " + group.getFullName() + " §7erhalten");

                                } else {
                                    sender.sendMessage(plugin.getPrefix() + "§cDieser Spieler existiert nicht!");
                                }
                            });
                        } else
                            sender.sendMessage(plugin.getPrefix() + "§cDiese Gruppe existiert nicht §8[§e/group list§8]");
                    } else
                        help(sender);
                    break;
                }
                default:
                    help(sender);
                    break;
            }
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(plugin.getPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        sender.sendMessage(plugin.getPrefix()+"§e/group info [Name]");
        sender.sendMessage(plugin.getPrefix()+"§e/group set [Name] [GroupID]");
        sender.sendMessage(plugin.getPrefix()+"§e/group list");
        sender.sendMessage(plugin.getPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }
}
