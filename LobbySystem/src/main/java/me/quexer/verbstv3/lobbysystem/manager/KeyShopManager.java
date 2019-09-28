package me.quexer.verbstv3.lobbysystem.manager;


import me.quexer.api.quexerapi.builder.EntityBuilder;
import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.builder.inventory.InventoryBuilder;
import me.quexer.api.quexerapi.event.EventManager;
import me.quexer.api.quexerapi.misc.AsyncTask;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class KeyShopManager {

    Inventory inventory;
    Villager villager;

    private LobbySystem plugin;

    public KeyShopManager(LobbySystem plugin) {
        this.plugin = plugin;
        villager = (Villager) plugin.getLocationManager().getKeyshopnpc().getWorld().spawnEntity(plugin.getLocationManager().getKeyshopnpc(), EntityType.VILLAGER);
        new EntityBuilder(villager, plugin.getInstance()).modify().setInvulnerable(true).setDisplayNameVisible(true).setDisplayName("§a§lKey-Shop").setDisplayNameVisible(true).setCanDespawn(false).setNoAI(true).setYawPitch(180, 0);

        InventoryBuilder builder = new InventoryBuilder("§e§lKeyShop", 27);
        builder.setItem(11, new ItemBuilder(Material.TRIPWIRE_HOOK).setName("§8➜ §51 §7Key").setLore("§a", "§7Kostet§8: §e5.000 Coins", "§a", "§7Klicke um einen Key zu kaufen!").toItemStack());
        builder.setItem(13, new ItemBuilder(Material.TRIPWIRE_HOOK, 3).setName("§8➜ §53 §7Keys").setLore("§a", "§7Kostet§8: §e13.500 Coins", "§a", "§7Klicke um drei Key zu kaufen!").toItemStack());
        builder.setItem(15, new ItemBuilder(Material.TRIPWIRE_HOOK, 5).setName("§8➜ §55 §7Keys").setLore("§a", "§7Kostet§8: §e20.000 Coins", "§a", "§7Klicke um fünf Key zu kaufen!").toItemStack());

        builder.addListener((player, inv, clickType, itemStack) -> {
            new AsyncTask(() -> {
            BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());

            if (itemStack.getType() == Material.TRIPWIRE_HOOK) {
                if (itemStack.getAmount() == 1) {
                    if (backendPlayer.getData().getCoins() >= 5000) {
                        backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() + 1);
                        backendPlayer.getData().setCoins(backendPlayer.getData().getCoins() - 5000);
                        plugin.getScoreboardManager().updateScoreboard(player);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 3, 3);
                    } else {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 3, 3);
                        player.sendMessage(plugin.getPrefix() + "§cDu musst mindestens §e5.000 Coins §cbesitzen");
                    }
                } else if (itemStack.getAmount() == 3) {
                    if (backendPlayer.getData().getCoins() >= 13500) {
                        backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() + 3);
                        backendPlayer.getData().setCoins(backendPlayer.getData().getCoins() - 13500);
                        plugin.getScoreboardManager().updateScoreboard(player);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 3, 3);
                    } else {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 3, 3);
                        player.sendMessage(plugin.getPrefix() + "§cDu musst mindestens §e12.500 Coins §cbesitzen");
                    }
                } else if (itemStack.getAmount() == 5) {
                    if (backendPlayer.getData().getCoins() >= 20000) {
                        backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() + 5);
                        backendPlayer.getData().setCoins(backendPlayer.getData().getCoins() - 13500);
                        plugin.getScoreboardManager().updateScoreboard(player);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 3, 3);
                    } else {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 3, 3);
                        player.sendMessage(plugin.getPrefix() + "§cDu musst mindestens §e20.000 Coins §cbesitzen");
                    }
                }
            }
            });
        });
        inventory = builder.build();
        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerInteractEntityEvent.class, (EventManager.EventListener<PlayerInteractEntityEvent>) event -> {
            event.setCancelled(true);
            if(event.getRightClicked().getType() == EntityType.VILLAGER) {

                if(event.getRightClicked().getCustomName() == "§a§lKey-Shop" || event.getRightClicked().getName() == "§a§lKey-Shop") {
                    event.getPlayer().openInventory(inventory);
                }
            }
        });



    }

    public Inventory getInventory() {
        return inventory;
    }
}
