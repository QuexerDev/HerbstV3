package me.quexer.verbstv3.lobbysystem.obj;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sun.plugin2.main.server.Plugin;

import javax.naming.Name;

public class Head extends Extra{

    private LobbySystem plugin;

    public Head(String name, EnumExtra enumExtra, ItemStack logo, Rarity rarity, LobbySystem plugin) {
        super(name, enumExtra, new ItemBuilder(logo).setLore("§a","§7§lTyp§8: §eHead","§a","§7§lSeltenheit§8: "+rarity.getName()).toItemStack(), rarity, plugin);
        this.plugin = plugin;
    }

    @Override
    public void equip(Player player) {
        player.getInventory().setHelmet(getLogo());
        player.sendMessage(LobbySystem.getLobbyPrefix()+"§7Du hast den §eHead §7von §e"+ getName()+" §7ausgerüstet§8!");
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 0.3F, 0.3F);
    }

    @Override
    public void unequip(Player player) {
        player.getInventory().setHelmet(null);
        player.sendMessage(LobbySystem.getLobbyPrefix()+"§cDu hast deinen aktuellen §eHead §centfernt");
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 0.3F, 0.3F);
    }


}
