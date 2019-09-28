package me.quexer.verbstv3.lobbysystem.obj;

import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.builder.inventory.InventoryBuilder;
import me.quexer.api.quexerapi.misc.AsyncTask;
import me.quexer.herbst.herbstplugin.enums.EnumExtra;
import me.quexer.herbst.herbstplugin.enums.Rarity;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import javax.persistence.Lob;
import java.util.List;
import java.util.Random;

public class CaseOpening {


    private BukkitTask task;
    private Player player;
    private EnumExtra win;
    private int high = 30;
    private List<Extra> extras;
    private LobbySystem plugin;

    public CaseOpening(Player player, LobbySystem plugin) {
        this.plugin = plugin;
        this.player = player;
        extras = plugin.getGadgetsManager().getExtras();
        plugin.getQuexerAPI().setMetadata(player, "case", 1);

    }

    public void start() {
        EnumExtra enumExtra = getRandomWin();
        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());
        Extra win = plugin.getGadgetsManager().getByType(enumExtra);
        InventoryBuilder builder = new InventoryBuilder("§a§lCase Opening", 27);
        for (int i = 0; i < builder.build().getSize(); i++) {
            builder.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§a").setDurability((short) 15).toItemStack());
        }
        builder.addListener((player1, inv, clickType, itemStack) -> {
        });
        builder.setItem(4, new ItemBuilder(Material.HOPPER).setName("§8§l⬇ §7Dein §e§lGewinn §8§l⬇").toItemStack());
        player.openInventory(builder.build());
        System.out.println(enumExtra);
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (high == 0) {

                task.cancel();
                player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1, 1);
                builder.setItem(13, win.getLogo());
                builder.setItem(22, plugin.getGadgetsManager().getByLogo(builder.build().getItem(13)).getRarity().getPane());
                if (win.hasExtra(player)) {
                    player.sendMessage(LobbySystem.getLobbyPrefix() + "§cDu besitzt das Extra§8: " + win.getName() + " §8[" + win.getRarity().getName() + "§8] §cbereits, daher wurden dir §eCoins 3§chinzugefügt");
                    backendPlayer.getData().setCoins(backendPlayer.getData().getCoins() + 2500);
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        plugin.getScoreboardManager().updateScoreboard(player);
                    });
                } else {
                    player.sendMessage("");
                    player.sendMessage(LobbySystem.getLobbyPrefix() + "§7Du hast das Extra§8: " + win.getName() + " §8[" + win.getRarity().getName() + "§8] §7gewonnen");
                    player.sendMessage("");
                    if (win.getRarity() == Rarity.LEGENDARY) {
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(player.getDisplayName() + " §7hat ein §6§lLEGENDARY §7Extra gewonnen§8!");
                        Bukkit.broadcastMessage("§7Extra§8: " + win.getName());
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§8§m--------------------------------------------------");
                    }
                    win.addExtra(player);
                    plugin.getQuexerAPI().removeMetadata(player, "case");
                }

            } else {
                builder.setItem(17, builder.build().getItem(16));
                builder.setItem(8, builder.build().getItem(7));
                builder.setItem(26, builder.build().getItem(25));

                builder.setItem(16, builder.build().getItem(15));
                builder.setItem(7, builder.build().getItem(6));
                builder.setItem(25, builder.build().getItem(24));

                builder.setItem(15, builder.build().getItem(14));
                builder.setItem(6, builder.build().getItem(5));
                builder.setItem(24, builder.build().getItem(23));

                builder.setItem(14, builder.build().getItem(13));
                builder.setItem(23, builder.build().getItem(22));
                builder.setItem(5, builder.build().getItem(23));

                builder.setItem(13, builder.build().getItem(12));
                builder.setItem(22, builder.build().getItem(21));

                builder.setItem(12, builder.build().getItem(11));
                builder.setItem(3, builder.build().getItem(2));
                builder.setItem(21, builder.build().getItem(20));

                builder.setItem(11, builder.build().getItem(10));
                builder.setItem(2, builder.build().getItem(1));
                builder.setItem(20, builder.build().getItem(19));

                builder.setItem(10, builder.build().getItem(9));
                builder.setItem(1, builder.build().getItem(0));
                builder.setItem(19, builder.build().getItem(18));


                builder.setItem(9, extras.get(new Random().nextInt(extras.size())).getLogo());
                builder.setItem(0, plugin.getGadgetsManager().getByLogo(builder.build().getItem(9)).getRarity().getPane());
                builder.setItem(18, plugin.getGadgetsManager().getByLogo(builder.build().getItem(9)).getRarity().getPane());
                player.playSound(player.getLocation(), Sound.WOOD_CLICK, 3, 3);
                high--;
            }
        }, 1, 3);

    }

    public EnumExtra getRandomWin() {
        EnumExtra win = null;
        Random random = new Random();
        int rdm = random.nextInt(100);
        Rarity rarity = null;
        if (rdm >= 91 && rdm <= 100) {
            rarity = Rarity.LEGENDARY;
        } else if (rdm >= 76 && rdm <= 90) {
            rarity = Rarity.EPIC;
        } else if (rdm >= 41 && rdm <= 75) {
            rarity = Rarity.RARE;
        } else if (rdm >= 0 && rdm <= 40) {
            rarity = Rarity.COMMON;
        }
        win = plugin.getGadgetsManager().getRarityExtrasMap().get(rarity).get(new Random().nextInt(plugin.getGadgetsManager().getRarityExtrasMap().get(rarity).size())).getEnumExtra();

        return win;
    }

}
