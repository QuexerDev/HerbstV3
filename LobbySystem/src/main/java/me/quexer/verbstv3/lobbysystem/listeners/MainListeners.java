package me.quexer.verbstv3.lobbysystem.listeners;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.builder.inventory.InventoryBuilder;
import me.quexer.api.quexerapi.event.EventManager;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.herbst.herbstplugin.obj.LobbyPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import me.quexer.verbstv3.lobbysystem.obj.CaseOpening;
import me.quexer.verbstv3.lobbysystem.obj.Effekt;
import me.quexer.verbstv3.lobbysystem.obj.Morph;
import org.bukkit.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainListeners {

    private LobbySystem plugin;


    public MainListeners(LobbySystem plugin) {
        this.plugin = plugin;
            plugin.getQuexerAPI().getEventManager().registerEvent(PlayerJoinEvent.class, (EventManager.EventListener<PlayerJoinEvent>) event -> {
                event.setJoinMessage(null);
                event.getPlayer().teleport(plugin.getLocationManager().getSpawn());
                LobbyPlayer lobbyPlayer = plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()).getData().getLobbyPlayer();

                    plugin.getScoreboardManager().setBoard(event.getPlayer());
                    event.getPlayer().getInventory().clear();
                    event.getPlayer().getInventory().setArmorContents(null);
                    event.getPlayer().setLevel(2019);
                    event.getPlayer().setExp(0);
                    plugin.getItemManager().setup(event.getPlayer());
                    Bukkit.getOnlinePlayers().forEach(o -> {
                        if (o.hasMetadata("hider")) {
                            o.hidePlayer(event.getPlayer());
                        }
                    });
                    if (lobbyPlayer.getDailyReward() <= System.currentTimeMillis()) {
                        event.getPlayer().sendTitle("§6§lHerbst.net", "§aDeine tägliche Belohnung ist verfügbar");
                    } else {
                        event.getPlayer().sendTitle("§6§lHerbst.net", "§7Willkommen");
                    }

                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin.getInstance(), () -> {
                    plugin.getDailyRewardManager().getNpc().spawn();
                    plugin.getDailyRewardManager().getNpc().injectNetty(event.getPlayer());
                }, 20*2);
                event.getPlayer().setGameMode(GameMode.ADVENTURE);
            });

            plugin.getQuexerAPI().getEventManager().registerEvent(PlayerQuitEvent.class, (EventManager.EventListener<PlayerQuitEvent>) event -> {
                event.setQuitMessage(null);
                if (event.getPlayer().hasMetadata("effekt"))
                    ((Effekt) event.getPlayer().getMetadata("effekt").get(0).value()).unequip(event.getPlayer());
                if (event.getPlayer().hasMetadata("head"))
                    event.getPlayer().getInventory().setHelmet(null);
                if (event.getPlayer().hasMetadata("morph"))
                    ((Morph) event.getPlayer().getMetadata("morph").get(0).value()).unequip(event.getPlayer());
                plugin.getDailyRewardManager().getNpc().ejectNetty(event.getPlayer());
            });

            plugin.getQuexerAPI().getEventManager().registerEvent(PlayerInteractEvent.class, (EventManager.EventListener<PlayerInteractEvent>) event -> {
                event.setCancelled(true);
                if(event.getAction() == Action.PHYSICAL) {
                    if(event.getPlayer().getLocation().getBlock().getType() == Material.STONE_PLATE) {
                        event.getPlayer().playEffect(event.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                        event.getPlayer().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 1);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ANVIL_LAND, 3, 3);
                        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().normalize().setY(3).multiply(0.5));
                    }
                    return;
                }
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType() == Material.CHEST) {
                        event.setCancelled(true);
                        if (event.getClickedBlock().hasMetadata("caseopening")) {
                            event.setCancelled(true);

                            BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString());
                            InventoryBuilder builder = new InventoryBuilder("§e§lCases", 27);
                            builder.setItem(13, new ItemBuilder(Material.TRIPWIRE_HOOK).setName("§8➜ §7Normale Keys").setLore("§a", "§7Du besitzt§8: §a" + backendPlayer.getData().getKeys(), "§a", "§7Klicke um einen Key zu benutzen!").toItemStack());

                            builder.addListener((player, inv, clickType, itemStack) -> {
                                    if (itemStack.getType() == Material.TRIPWIRE_HOOK) {
                                        if (backendPlayer.getData().getKeys() > 0) {
                                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.WOOD_CLICK, 1, 1);
                                            backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() - 1);
                                            plugin.getScoreboardManager().updateScoreboard(event.getPlayer());
                                            CaseOpening opening = new CaseOpening(player, plugin);
                                            opening.start();
                                        } else {
                                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.WOOD_CLICK, 3, 3);
                                            player.closeInventory();
                                            player.sendMessage(plugin.getPrefix() + "§cDu besitzt keine §7Normalen §cKeys!");
                                        }
                                    }
                            });
                            event.getPlayer().openInventory(builder.build());
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CHEST_OPEN, 0.3F, 0.3F);

                        }
                    }
                }


            });

    }
}
