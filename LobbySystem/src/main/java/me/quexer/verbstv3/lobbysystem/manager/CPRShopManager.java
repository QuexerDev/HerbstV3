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

public class CPRShopManager {

    private LobbySystem plugin;
    Inventory inventory;
    Villager villager;

    public CPRShopManager(LobbySystem plugin) {
        this.plugin = plugin;
        villager = (Villager) plugin.getLocationManager().getCprShopNPC().getWorld().spawnEntity(plugin.getLocationManager().getCprShopNPC(), EntityType.VILLAGER);
        new EntityBuilder(villager, plugin.getInstance()).modify().setInvulnerable(true).setDisplayNameVisible(true).setDisplayName("§a§lCPR-Shop").setDisplayNameVisible(true).setCanDespawn(false).setNoAI(true).setYawPitch(180, 0);

        InventoryBuilder builder = new InventoryBuilder("§e§lCPRShop", 27);
        builder.setItem(13, new ItemBuilder(Material.GOLD_INGOT).setName("§8➜ §e+1 §7CPR").setLore("§a", "§7Kostet§8: §e2.500 Coins", "§a", "§7Klicke um einen +1 CPR zu kaufen!").toItemStack());

        builder.addListener((player, inv, clickType, itemStack) -> {
            new AsyncTask(() -> {
                BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());

                if (itemStack.getType() == Material.GOLD_INGOT) {
                        if (backendPlayer.getData().getCoins() >= 2500) {
                            backendPlayer.getData().setCpr(backendPlayer.getData().getCpr() + 1);
                            backendPlayer.getData().setCoins(backendPlayer.getData().getCoins() - 2500);
                            plugin.getScoreboardManager().updateScoreboard(player);
                            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 3, 3);
                            player.sendMessage(plugin.getPrefix()+"§7Deine CPR beträgt§8: §e"+backendPlayer.getData().getCpr());
                        } else {
                            player.closeInventory();
                            player.playSound(player.getLocation(), Sound.NOTE_BASS, 3, 3);
                            player.sendMessage(plugin.getPrefix() + "§cDu musst mindestens §e2.500 Coins §cbesitzen");
                        }
                }
            });
        });
        inventory = builder.build();
        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerInteractEntityEvent.class, (EventManager.EventListener<PlayerInteractEntityEvent>) event -> {
            event.setCancelled(true);
            if(event.getRightClicked().getType() == EntityType.VILLAGER) {

                if(event.getRightClicked().getCustomName() == "§a§lCPR-Shop" || event.getRightClicked().getName() == "§a§lCPR-Shop") {
                    event.getPlayer().openInventory(inventory);
                }
            }
        });

    }

}
