package me.quexer.herbst.herbstplugin.enums;

import jdk.nashorn.internal.objects.annotations.Getter;
import me.quexer.api.quexerapi.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sun.plugin2.message.GetNameSpaceMessage;

public enum Rarity {

    COMMON("§7Common", new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§7§lCommon").setDurability((short) 8).toItemStack()),
    RARE("§bRare", new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§b§lRare").setDurability((short) 3).toItemStack()),
    EPIC("§5Epic", new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§5§lEpic").setDurability((short) 10).toItemStack()),
    LEGENDARY("§6§lLegendary", new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§6§lLegendary").setDurability((short) 1).toItemStack());


    private String name;
    private ItemStack pane;

    Rarity(String name, ItemStack pane) {
        this.name = name;
        this.pane = pane;
    }

    public ItemStack getPane() {
        return pane;
    }

    public String getName() {
        return name;
    }
}
