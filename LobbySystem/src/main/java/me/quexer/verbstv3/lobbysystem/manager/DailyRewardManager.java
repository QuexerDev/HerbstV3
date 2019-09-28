package me.quexer.verbstv3.lobbysystem.manager;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.builder.NPC;
import me.quexer.api.quexerapi.builder.inventory.InventoryBuilder;
import me.quexer.api.quexerapi.event.EventManager;
import me.quexer.api.quexerapi.misc.AsyncTask;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Material;
import org.bukkit.Sound;
import java.util.Random;

public class DailyRewardManager {


    private NPC npc;
    private LobbySystem plugin;


    public DailyRewardManager(LobbySystem plugin) {
        this.plugin = plugin;
        npc = new NPC( "Notch","§a§lDaily Reward","§a§lDaily Reward", new Random().nextInt(10000),plugin.getLocationManager().getDailyRewardNPC(), Material.GOLD_INGOT,true);
        //npc.changePlayerlistName("§8➜ §a§lDaily Reward");
        //npc.spawn();
        //String skinName, String name, String tablist, int entityID, Location location, Material inHand, boolean hideTablist


        plugin.getQuexerAPI().getEventManager().registerEvent(NPC.PlayerInteractNPCEvent.class, (EventManager.EventListener<NPC.PlayerInteractNPCEvent>) event -> {
            if(npc.equals(plugin.getDailyRewardManager().getNpc())) {

                BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString());
                InventoryBuilder builder = new InventoryBuilder("§a§lDaily Reward", 27);
                new AsyncTask(() -> {
                    if (backendPlayer.getData().getLobbyPlayer().getDailyReward() <= System.currentTimeMillis()) {
                        builder.setItem(13, new ItemBuilder(Material.STORAGE_MINECART).setName("§a§lTägliche Belohnung§8:").setLore(
                                "§a",
                                "§aSpieler§8:",
                                "§a",
                                "§8➜ §e2.000 Coins",
                                "§8➜ §51x §7Key",
                                "§a",
                                "§6§lPremium§8:",
                                "§a",
                                "§8➜ §e5.000 Coins",
                                "§8➜ §52x §7Key", "§b", "§a", "§7Als §6§lPremium §7erhältst du beide Belohnungen").toItemStack());
                    } else {
                        builder.setItem(13, new ItemBuilder(Material.MINECART).setName("§c§lTägliche Belohnung§8:").setLore(
                                "§a",
                                "§aSpieler§8:",
                                "§a",
                                "§8➜ §e2.000 Coins",
                                "§8➜ §51x §7Key",
                                "§a",
                                "§6§lPremium§8:",
                                "§a",
                                "§8➜ §e5.000 Coins",
                                "§8➜ §52x §7Key", "§b", "§a", "§7Als §6§lPremium §7erhältst du beide Belohnungen", "§cDu hast deine belohnung bereits abgeholt").toItemStack());
                    }
                    builder.addListener((player, inv, clickType, itemStack) -> {
                        if (itemStack.getType() == Material.STORAGE_MINECART) {
                            backendPlayer.getData().getLobbyPlayer().setDailyReward(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
                            if (backendPlayer.getGroup().hasPermission(2)) {
                                backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() + 3);
                                backendPlayer.getData().setCoins(backendPlayer.getData().getCoins()+7000);
                                player.sendMessage("");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§7Du hast Folgendes erhalten§8:");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§8➜ §e7.000 Coins");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§8➜ §53x §7Key");
                                player.sendMessage("");
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 3);

                                plugin.getScoreboardManager().updateScoreboard(player);
                            } else {
                                backendPlayer.getData().setKeys(backendPlayer.getData().getKeys() + 1);
                                backendPlayer.getData().setCoins(backendPlayer.getData().getCoins()+2000);
                                player.sendMessage("");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§7Du hast Folgendes erhalten§8:");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§8➜ §e2.000 Coins");
                                player.sendMessage(LobbySystem.getLobbyPrefix() + "§8➜ §51x §7Key");
                                player.sendMessage("");
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 3);

                                plugin.getScoreboardManager().updateScoreboard(player);
                            }
                        } else if (itemStack.getType() == Material.MINECART) {
                            player.closeInventory();
                            player.sendMessage(LobbySystem.getLobbyPrefix() + "§cDu hast deine belohnung bereits abgeholt. Du musst §e24 Stunden §cwarten, um die nächste zu erhalten!");
                        }
                    });
                });
                event.getPlayer().openInventory(builder.build());

            }

        });

    }

    public NPC getNpc() {
        return npc;
    }
}
