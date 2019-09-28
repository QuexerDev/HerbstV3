package me.quexer.verbstv3.lobbysystem.manager;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.builder.inventory.InventoryBuilder;
import me.quexer.api.quexerapi.misc.AsyncTask;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import me.quexer.verbstv3.lobbysystem.obj.Effekt;
import me.quexer.verbstv3.lobbysystem.obj.Head;
import me.quexer.verbstv3.lobbysystem.obj.Morph;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    private Inventory navigator;
    private Inventory extras;
    private Inventory lobbySwitcher;

    private LobbySystem plugin;

    public InventoryManager(LobbySystem plugin) {
        this.plugin = plugin;
        navigator = new InventoryBuilder("§a§lNavigator", 45)
                .setItem(4, new ItemBuilder(Material.IRON_SWORD).setName("§8» §6SurvivalGames").setLore("§a","§7Zu den §eServern §7teleportieren").toItemStack())
                .setItem(19, new ItemBuilder(Material.SANDSTONE).setName("§8» §eBuildFFA").setLore("§a","§7Zu den §eServern §7teleportieren").toItemStack())
                .setItem(22, new ItemBuilder(Material.EYE_OF_ENDER).setName("§8» §eSpawn").setLore("§a","§7Zu dem §eSpawn §7teleportieren").toItemStack())
                .setItem(25, new ItemBuilder(Material.GRASS).setName("§8» §cSkyBlock").setLore("§a","§cComming soon...").toItemStack())
                .setItem(38, new ItemBuilder(Material.EMERALD).setName("§8» §aTägliche Belohnung").setLore("§a","§7Zu dem §eNPC §7teleportieren").toItemStack())
                .setItem(42, new ItemBuilder(Material.CHEST).setName("§8» §6Case Opening").setLore("§a","§7Zu der §eChest §7teleportieren").toItemStack())
                .setItem(40, new ItemBuilder(Material.GOLD_INGOT).setName("§8» §6CPR-Shop").setLore("§a","§7Zu dem §eCPR-Shop §7teleportieren").toItemStack())
                .addListener((player, inv, clickType, itemStack) -> {
                  switch (itemStack.getType()) {
                      case IRON_SWORD: player.teleport(plugin.getLocationManager().getSurvivalGames()); break;
                      case SANDSTONE: player.teleport(plugin.getLocationManager().getBuildFFA()); break;
                      case EYE_OF_ENDER: player.teleport(plugin.getLocationManager().getSpawn()); break;
                      case IRON_BLOCK: player.teleport(plugin.getLocationManager().getDefuse()); break;
                      case EMERALD: player.teleport(plugin.getLocationManager().getDailyReward()); break;
                      case CHEST: player.teleport(plugin.getLocationManager().getCaseOpening()); break;
                      case GOLD_INGOT: player.teleport(plugin.getLocationManager().getCprShop()); break;
                  }
                })
                .build();
        extras = new InventoryBuilder("§a§lExtras", 27)
                .setItem(10, new ItemBuilder(Material.BLAZE_POWDER).setName("§8» §6§lEffekte").toItemStack())
                .setItem(13, new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner("Quexer").setName("§8» §c§lHeads").toItemStack())
                .setItem(16, new ItemBuilder(Material.MONSTER_EGG).setName("§8» §e§lMorphs").toItemStack())
                .addListener((player, inv, clickType, itemStack) -> {
                    BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());
                    switch (itemStack.getType()) {
                        case BLAZE_POWDER:
                            new InventoryBuilder("§a§lEffekte", 54).setItem(13, new ItemBuilder(Material.PAPER).setName("§7Alle §eEffekte §7laden...").toItemStack())
                                    .addListener((player1, inv1, clickType1, itemStack1) -> {
                                        new AsyncTask(() -> {
                                        if(itemStack1.getType() == Material.PAPER && itemStack1.getItemMeta().getDisplayName().contains("§7Alle §e")) {
                                            final int[] i = {0};
                                            inv1.setItem(13, new ItemStack(Material.AIR));
                                            inv1.setItem(49, new ItemBuilder(Material.BARRIER).setName("§cEffekt entfernen").toItemStack());
                                            backendPlayer.getData().getLobbyPlayer().getExtraTypes().forEach(enumExtra -> {
                                            if (plugin.getGadgetsManager().getByType(enumExtra) instanceof Effekt) {
                                                inv1.setItem(i[0], plugin.getGadgetsManager().getByType(enumExtra).getLogo());
                                                i[0]++;
                                            }
                                            });
                                        } else if(itemStack1.getType() == Material.BARRIER){
                                            ((Effekt) player.getMetadata("effekt").get(0).value()).unequip(player);
                                        } else {
                                            plugin.getGadgetsManager().getByLogo(itemStack1).equip(player);
                                        }
                                        });
                                    }).show(player);
                            break;
                        case SKULL_ITEM:
                            new InventoryBuilder("§a§lHeads", 54).setItem(13, new ItemBuilder(Material.PAPER).setName("§7Alle §eHeads §7laden...").toItemStack())
                                    .addListener((player1, inv1, clickType1, itemStack1) -> {
                                        new AsyncTask(() -> {
                                        if(itemStack1.getType() == Material.PAPER && itemStack1.getItemMeta().getDisplayName().contains("§7Alle §e")) {
                                            final int[] i = {0};
                                            inv1.setItem(13, new ItemStack(Material.AIR));
                                            inv1.setItem(49, new ItemBuilder(Material.BARRIER).setName("§cEffekt entfernen").toItemStack());
                                            backendPlayer.getData().getLobbyPlayer().getExtraTypes().forEach(enumExtra -> {
                                                if (plugin.getGadgetsManager().getByType(enumExtra) instanceof Head) {
                                                    inv1.setItem(i[0], plugin.getGadgetsManager().getByType(enumExtra).getLogo());
                                                    i[0]++;
                                                }
                                            });
                                        } else if(itemStack1.getType() == Material.BARRIER){
                                            player.getInventory().setHelmet(null);
                                            player.sendMessage(plugin.getPrefix() + "§cDu hast den aktuellen §eHead §centfernt§8!");
                                        } else {
                                            plugin.getGadgetsManager().getByLogo(itemStack1).equip(player);
                                        }
                                        });
                                    }).show(player);
                            break;
                        case MONSTER_EGG:
                            new InventoryBuilder("§a§lMorphs", 54).setItem(13, new ItemBuilder(Material.PAPER).setName("§7Alle §eMorphs §7laden...").toItemStack())
                                    .addListener((player1, inv1, clickType1, itemStack1) -> {
                                        new AsyncTask(() -> {
                                        if(itemStack1.getType() == Material.PAPER && itemStack1.getItemMeta().getDisplayName().contains("§7Alle §e")) {
                                            final int[] i = {0};
                                            inv1.setItem(13, new ItemStack(Material.AIR));
                                            inv1.setItem(49, new ItemBuilder(Material.BARRIER).setName("§cEffekt entfernen").toItemStack());
                                            backendPlayer.getData().getLobbyPlayer().getExtraTypes().forEach(enumExtra -> {
                                                if (plugin.getGadgetsManager().getByType(enumExtra) instanceof Morph) {
                                                    inv1.setItem(i[0], plugin.getGadgetsManager().getByType(enumExtra).getLogo());
                                                    i[0]++;
                                                }
                                            });
                                        } else if(itemStack1.getType() == Material.BARRIER){
                                            ((Morph) player.getMetadata("morph").get(0).value()).unequip(player);
                                        } else {
                                            plugin.getGadgetsManager().getByLogo(itemStack1).equip(player);
                                        }
                                        });
                                    }).show(player);
                            break;
                    }
                })
                .build();
    }

    public Inventory getNavigator() {
        return navigator;
    }

    public Inventory getExtras() {
        return extras;
    }

    public Inventory getLobbySwitcher() {
        return lobbySwitcher;
    }
}
