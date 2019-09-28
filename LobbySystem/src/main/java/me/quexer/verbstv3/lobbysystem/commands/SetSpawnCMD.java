package me.quexer.verbstv3.lobbysystem.commands;

import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCMD implements CommandExecutor {

    private LobbySystem plugin;

    public SetSpawnCMD(LobbySystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if(plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getGroup().hasPermission(12)) {
            if(args.length == 1) {
                plugin.getQuexerAPI().getLocationAPI().setLocation(args[0].toLowerCase(), player.getLocation());
                player.sendMessage(LobbySystem.getLobbyPrefix()+"§aDu hast die Location erfolgreich gesetzt§8!");
            } else {
                player.sendMessage(LobbySystem.getLobbyPrefix()+"§7Benutze§8: §c/setSpawn [Name]");
            }
        }
        return true;
    }
}
