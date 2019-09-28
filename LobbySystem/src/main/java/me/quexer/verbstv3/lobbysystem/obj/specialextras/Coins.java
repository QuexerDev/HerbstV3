package me.quexer.verbstv3.lobbysystem.obj.specialextras;

import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import me.quexer.verbstv3.lobbysystem.obj.Extra;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sun.plugin2.main.server.Plugin;

import javax.persistence.Lob;

public class Coins extends Extra {

    private int amount;
    private LobbySystem plugin;

    public Coins(String name, EnumExtra enumExtra, ItemStack logo, Rarity rarity, int amount, LobbySystem plugin) {
        super(name, enumExtra, logo, rarity, plugin);
        this.plugin = plugin;
        this.amount = amount;

    }



    @Override
    public void addExtra(Player player) {
        plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getData().setCoins(plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getData().getCoins() + amount);
    }

    @Override
    public void equip(Player player) {

    }

    @Override
    public void unequip(Player player) {

    }
}
