package me.quexer.verbstv3.lobbysystem.manager;


import me.quexer.api.quexerapi.builder.ItemBuilder;
import me.quexer.api.quexerapi.event.EventManager;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private ItemStack navigator;
    private ItemStack playerHider_on;
    private ItemStack playerHider_off;
    private ItemStack extras;
    private ItemStack nick_on;
    private ItemStack nick_off;
    private ItemStack lobbySwitcher;

    private List<Player> used = new ArrayList<>();
    private LobbySystem plugin;

    public ItemManager(LobbySystem plugin) {
        this.plugin = plugin;
        navigator = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=").setName("§e§lNavigator §8[§7Rechtsklick§8]").toItemStack();
        playerHider_on = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY5ZjRmMWE0MzQ2ZDA0YzllOTY2ZDRmZTU2ZDc3M2YyZmE2MmU0Nzg0YTRjMjg1MzczOGE5ZjJmNWNkMjY5In19fQ==").setName("§e§lSpieler Verstecken §8[§aAktiviert§8]").toItemStack();
        playerHider_off = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQxNTUzZGI3NzMzOWI0OGZiZjM5YTNkMDliM2VkN2JhNWVlNDllMTZhYzU2YTlmNmI5ZmQ1M2NmNGEifX19").setName("§e§lSpieler Verstecken §8[§cDeaktiviert§8]").toItemStack();
        extras = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNmRjMmJiZjUxYzM2Y2ZjNzcxNDU4NWE2YTU2ODNlZjJiMTRkNDdkOGZmNzE0NjU0YTg5M2Y1ZGE2MjIifX19").setName("§e§lExtras §8[§7Rechtsklick§8]").toItemStack();
        nick_on = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRlMjQ3YzA4ZjQwMWQ1NTQzOGM4ZDEyNTFmYjFhYTc3ZjdkOTc3NDI4NDM4ZjE3OGM4ZDg3OGIzZmEzYWRhYSJ9fX0=").setName("§e§lNick §8[§aAktiviert§8]").toItemStack();
        nick_off = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY5ODIxNzcyY2EyNjczZjRhY2I1MzkzZDEyNmIyZTYyZTgyY2U4NTVhNDljZmVlYTc3ODMwYzVkMTI0YSJ9fX0=").setName("§e§lNick §8[§cDeaktiviert§8]").toItemStack();
        lobbySwitcher = new ItemBuilder(Material.SKULL_ITEM).setSkullID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg5MDQyMDgyYmI3YTc2MThiNzg0ZWU3NjA1YTEzNGM1ODgzNGUyMWUzNzRjODg4OTM3MTYxMDU3ZjZjNyJ9fX0=").setName("§e§lLobbySwitcher §8[§7Rechtsklick§8]").toItemStack();

        plugin.getQuexerAPI().getEventManager().registerEvent(PlayerInteractEvent.class, (EventManager.EventListener<PlayerInteractEvent>) event -> {
            try {
                if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lNavigator §8[§7Rechtsklick§8]")) {
                    event.getPlayer().openInventory(plugin.getInventoryManager().getNavigator());
                } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lSpieler Verstecken §8[§aAktiviert§8]")) {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().setItemInHand(playerHider_off);
                        plugin.getQuexerAPI().removeMetadata(event.getPlayer(), "hider");
                        Bukkit.getOnlinePlayers().forEach(o -> event.getPlayer().showPlayer(o));
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 0.3F, 0.3F);
                        used.add(event.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> used.remove(event.getPlayer()), 5 * 20);
                    } else {
                        event.getPlayer().sendMessage(LobbySystem.getLobbyPrefix() + "§cBitte warte einen Moment, bis du dieses Item benutzt!");
                    }
                } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lSpieler Verstecken §8[§cDeaktiviert§8]")) {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().setItemInHand(playerHider_on);
                        plugin.getQuexerAPI().setMetadata(event.getPlayer(), "hider", "huiod");
                        Bukkit.getOnlinePlayers().forEach(o -> event.getPlayer().hidePlayer(o));
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 3, 3);
                        used.add(event.getPlayer());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> used.remove(event.getPlayer()), 5 * 20);
                    } else {
                        event.getPlayer().sendMessage(LobbySystem.getLobbyPrefix() + "§cBitte warte einen Moment, bis du dieses Item benutzt!");
                    }
                } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lExtras §8[§7Rechtsklick§8]")) {
                    event.getPlayer().openInventory(plugin.getInventoryManager().getExtras());
                } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lNick §8[§cDeaktiviert§8]")) {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().setItemInHand(nick_on);
                        plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()).getData().setNick(true);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 3, 3);
                        used.add(event.getPlayer());
                        plugin.getBackendManager().savePlayer(plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()));
                        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> used.remove(event.getPlayer()), 5 * 20);
                    } else {
                        event.getPlayer().sendMessage(LobbySystem.getLobbyPrefix() + "§cBitte warte einen Moment, bis du dieses Item benutzt!");
                    }
                } else if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("§e§lNick §8[§aAktiviert§8]")) {
                    if (!used.contains(event.getPlayer())) {
                        event.getPlayer().setItemInHand(nick_off);
                        plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()).getData().setNick(false);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 0.3F, 0.3F);
                        used.add(event.getPlayer());
                        plugin.getBackendManager().savePlayer(plugin.getBackendManager().getPlayer(event.getPlayer().getUniqueId().toString()));
                        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> used.remove(event.getPlayer()), 5 * 20);
                    } else {
                        event.getPlayer().sendMessage(LobbySystem.getLobbyPrefix() + "§cBitte warte einen Moment, bis du dieses Item benutzt!");
                    }

                }
            } catch (Exception e) {

            }
        });
    }

    public void setup(Player player) {
        BackendPlayer backendPlayer = plugin.getBackendManager().getPlayer(player.getUniqueId().toString());
        player.getInventory().setItem(0, navigator);
        if (player.hasMetadata("hider"))
            player.getInventory().setItem(1, playerHider_on);
        else
            player.getInventory().setItem(1, playerHider_off);
        player.getInventory().setItem(4, extras);
        if (backendPlayer.getGroup().hasPermission(4)) {
            if (backendPlayer.getData().isNick())
                player.getInventory().setItem(7, nick_on);
            else
                player.getInventory().setItem(7, nick_off);
        }
        //player.getInventory().setItem(8, lobbySwitcher);
    }
}
