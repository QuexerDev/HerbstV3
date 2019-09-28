package me.quexer.verbstv3.lobbysystem.obj;

import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class Extra {

    private String name;
    private EnumExtra enumExtra;
    private ItemStack logo;
    private Rarity rarity;
    private LobbySystem plugin;

    public Extra(String name, EnumExtra enumExtra, ItemStack logo, Rarity rarity, LobbySystem plugin) {
        this.name = name;
        this.plugin = plugin;
        this.enumExtra = enumExtra;
        this.logo = logo;
        this.rarity = rarity;
    }

    public boolean hasExtra(Player player) {
        boolean b = plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getData().getLobbyPlayer().getExtraTypes().contains(enumExtra);
        if(b) {
            return true;
        } else {

            return false;
        }
    }
    public void addExtra(Player player) {
        plugin.getBackendManager().getPlayer(player.getUniqueId().toString()).getData().getLobbyPlayer().getExtraTypes().add(enumExtra);
    }

    public abstract void equip(Player player);
    public abstract void unequip(Player player);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumExtra getEnumExtra() {
        return enumExtra;
    }

    public void setEnumExtra(EnumExtra enumExtra) {
        this.enumExtra = enumExtra;
    }

    public ItemStack getLogo() {
        return logo;
    }

    public void setLogo(ItemStack logo) {
        this.logo = logo;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
