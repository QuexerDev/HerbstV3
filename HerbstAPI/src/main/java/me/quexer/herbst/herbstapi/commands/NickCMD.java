package me.quexer.herbst.herbstapi.commands;

import me.quexer.api.quexerapi.api.NickAPI;
import me.quexer.herbst.herbstapi.HerbstAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCMD implements CommandExecutor {

    private HerbstAPI plugin;

    public NickCMD(HerbstAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getGroup().hasPermission(3)) {
            if(plugin.isNickOnThisServer()) {
                if (NickAPI.hasNick(player) == false) {
                    plugin.getBackendManager().nick(player);
                } else {
                    plugin.getBackendManager().unnick(player);
                }
            }
        } else
            player.sendMessage(plugin.getPrefix() + "§cDazu hast du keine Rechte!");
        return true;
    }
}
