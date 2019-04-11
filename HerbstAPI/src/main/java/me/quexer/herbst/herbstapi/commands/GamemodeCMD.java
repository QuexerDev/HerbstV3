package me.quexer.herbst.herbstapi.commands;

import me.quexer.herbst.herbstapi.HerbstAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD implements CommandExecutor {

    private HerbstAPI plugin;

    public GamemodeCMD(HerbstAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getGroup().hasPermission(12)) {
            if (args.length == 1) {
                boolean var6 = false;

                int s;
                try {
                    s = Integer.parseInt(args[0]);
                } catch (NumberFormatException var10) {
                    player.sendMessage(plugin.getPrefix() + "§cDu musst eine Zahl angeben");
                    return true;
                }

                if (s == 0) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(plugin.getPrefix() + "§7Dein Spielmodus wurde zu §eSurvival §7geändert.");
                } else if (s == 1) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(plugin.getPrefix() + "§7Dein Spielmodus wurde zu §eKreativ §7geändert.");
                } else if (s == 2) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(plugin.getPrefix() + "§7Dein Spielmodus wurde zu §eAdventure §7geändert.");
                } else if (s == 3) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(plugin.getPrefix() + "§7Dein Spielmodus wurde zu §eSpectator §7geändert.");
                } else {
                    player.sendMessage(plugin.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
                }
            } else if (args.length == 2) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    player.sendMessage(plugin.getPrefix() + "§cDieser Spieler ist nicht Online!");
                    return true;
                }

                boolean var7 = false;

                int s;
                try {
                    s = Integer.parseInt(args[1]);
                } catch (NumberFormatException var9) {
                    player.sendMessage(plugin.getPrefix() + "§cDu musst eine Zahl angeben");
                    return true;
                }

                if (s == 0) {
                    t.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eSurvival §7geändert");
                } else if (s == 1) {
                    t.setGameMode(GameMode.CREATIVE);
                    player.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eKreativ §7geändert");
                } else if (s == 2) {
                    t.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eAdventure §7geändert");
                } else if (s == 3) {
                    t.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("§7Du hast den Spielmodus von " + t.getDisplayName() + " §7zu §eSpectator §7geändert");
                } else {
                    player.sendMessage(plugin.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
                }
            } else
                player.sendMessage(plugin.getPrefix() + "§7Benutze§8: §7/Gamemode <Spieler> <0/1/2/3>");
        }
        return true;

    }

}
