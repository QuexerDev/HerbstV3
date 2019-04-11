package me.quexer.herbst.herbstapi.commands;

import me.quexer.herbst.herbstapi.HerbstAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CoinsCMD implements CommandExecutor {

    private HerbstAPI plugin;


    public CoinsCMD(HerbstAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        sender.sendMessage(String.format(plugin.getPrefix() + "§7Du hast §e" + new DecimalFormat("###,###,###,###,###,###").format(plugin.getBackendManager().getPlayer(((Player) sender).getUniqueId().toString()).getData().getCoins()) + " §7Coin(s)§8."));
        sender.sendMessage(String.format(plugin.getPrefix() + "§7Du hast §e" + new DecimalFormat("###,###,###,###,###,###").format(plugin.getBackendManager().getPlayer(((Player) sender).getUniqueId().toString()).getData().getKeys()) + " §7Key(s)§8."));
        sender.sendMessage(String.format(plugin.getPrefix() + "§7Du hast §e" + new DecimalFormat("###,###,###,###,###,###").format(plugin.getBackendManager().getPlayer(((Player) sender).getUniqueId().toString()).getData().getCpr()) + " §7Coin(s) pro Runde (CPR)§8."));
        return true;
    }
}
