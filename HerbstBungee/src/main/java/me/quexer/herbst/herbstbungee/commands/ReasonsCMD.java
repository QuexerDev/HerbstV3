package me.quexer.herbst.herbstbungee.commands;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReasonsCMD extends Command {

    private HerbstBungee plugin;

    public ReasonsCMD(HerbstBungee plugin) {
        super("reasons");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (plugin.getBackendManager().getPlayer(((ProxiedPlayer) sender).getUniqueId().toString()).getGroup().getLevelID() > 8) {
            sender.sendMessage(plugin.getPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
            sender.sendMessage(plugin.getPrefix()+"§7>> §eBan §7<<");
            sender.sendMessage(plugin.getPrefix()+"§cHacking §8- §c#1");
            sender.sendMessage(plugin.getPrefix()+"§cTrolling §8- §c#2");
            sender.sendMessage(plugin.getPrefix()+"§cTeaming §8- §c#3");
            sender.sendMessage(plugin.getPrefix()+"§cBugusing §8- §c#4");
            sender.sendMessage(plugin.getPrefix()+"§cSkin §8- §c#5");
            sender.sendMessage(plugin.getPrefix()+"§cName §8- §c#6");
            sender.sendMessage(plugin.getPrefix()+"§cReport-Ausnutzung §8- §c#7");
            sender.sendMessage(plugin.getPrefix()+"§cBannumgehung §8- §c#8");
            sender.sendMessage(plugin.getPrefix()+"§cHausverbot §8- §c#9");
            sender.sendMessage(plugin.getPrefix()+"§7>> §eMute §7<<");
            sender.sendMessage(plugin.getPrefix()+"§cBeleidigung §8- §c#10");
            sender.sendMessage(plugin.getPrefix()+"§cRassismus §8- §c#11");
            sender.sendMessage(plugin.getPrefix()+"§cWerbung §8- §c#12");
            sender.sendMessage(plugin.getPrefix()+"§cSpamming §8- §c#13");
            sender.sendMessage(plugin.getPrefix()+"§cProvokation §8- §c#14");
            sender.sendMessage(plugin.getPrefix()+"§cSchweigepflicht §8-§c#15");
            sender.sendMessage(plugin.getPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        }
    }
}
