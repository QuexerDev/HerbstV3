package me.quexer.herbst.herbstapi;

import me.quexer.herbst.herbstapi.commands.*;
import me.quexer.herbst.herbstapi.listeners.PlayerListeners;
import me.quexer.herbst.herbstapi.utils.TablistManager;
import me.quexer.herbst.herbstplugin.HerbstPlugin;
import org.bukkit.Bukkit;

public final class HerbstAPI extends HerbstPlugin {

    private HerbstPlugin plugin;
    private TablistManager tablistManager;


    @Override
    public void init() {
        plugin = this;
        
        Bukkit.getPluginCommand("nick").setExecutor(new NickCMD(this));
        Bukkit.getPluginCommand("tp").setExecutor(new TeleportCMD(this));
        Bukkit.getPluginCommand("gm").setExecutor(new GamemodeCMD(this));
        Bukkit.getPluginCommand("coins").setExecutor(new CoinsCMD(this));
        Bukkit.getPluginCommand("help").setExecutor(new HelpCMD(this));
       // Bukkit.getPluginCommand("group").setExecutor(new GroupCMD(this));

        tablistManager = new TablistManager(this);
        new PlayerListeners(this);
    }

    @Override
    public void disable() {

    }

    public HerbstPlugin getPlugin() {
        return plugin;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }
}
