package me.quexer.api.quexerapi.builder.inventory;


import me.quexer.api.quexerapi.QuexerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class InventoryBuilder {
    private Inventory inventory;

    public InventoryBuilder(String name, int size, InventoryHolder owner) {
        this.inventory = Bukkit.createInventory(owner, size, name);
    }

    public InventoryBuilder(InventoryHolder owner, InventoryType type) {
        this.inventory = Bukkit.createInventory(owner, type);
    }

    public InventoryBuilder(String name, int size) {
        this.inventory = Bukkit.createInventory((InventoryHolder)null, size, name);
    }

    public InventoryBuilder addListener(ItemListener listener) {
        QuexerAPI.getInventoryHandler().registerListener(this.inventory, listener);
        return this;
    }

    public void refresh() {
        Iterator var1 = this.inventory.getViewers().iterator();

        while(var1.hasNext()) {
            HumanEntity holder = (HumanEntity)var1.next();
            holder.closeInventory();
            holder.openInventory(this.inventory);
        }

    }

    public InventoryBuilder addItem(ItemStack stack) {
        this.inventory.addItem(new ItemStack[]{stack});
        return this;
    }

    public InventoryBuilder setItem(int slot, ItemStack stack) {
        this.inventory.setItem(slot, stack);
        return this;
    }

    public Inventory build() {
        return this.inventory;
    }

    public InventoryBuilder show(HumanEntity... viewers) {
        this.refresh();
        HumanEntity[] var2 = viewers;
        int var3 = viewers.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            HumanEntity viewer = var2[var4];
            viewer.openInventory(this.build());
        }

        return this;
    }
}
