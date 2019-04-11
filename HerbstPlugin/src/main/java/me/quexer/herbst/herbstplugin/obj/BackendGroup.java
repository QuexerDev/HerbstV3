package me.quexer.herbst.herbstplugin.obj;

import me.quexer.herbst.herbstplugin.HerbstPlugin;
import me.quexer.herbst.herbstplugin.interfaces.IBackendGroup;
import org.bukkit.Bukkit;

import java.util.UUID;

public class BackendGroup implements IBackendGroup {


    private String full;
    private String prefix;
    private String color;
    private int levelID;
    private int tabID;

    public BackendGroup(String full, String prefix, String color, int levelID, int tabID) {
        this.full = full;
        this.prefix = prefix;
        this.color = color;
        this.levelID = levelID;
        this.tabID = tabID;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public int getLevelID() {
        return levelID;
    }

    @Override
    public int tabID() {
        return tabID;
    }

    @Override
    public boolean hasPermission(int level) {
        return (levelID >= level);
    }

    @Override
    public String getFullName() {
        return full;
    }

    @Override
    public String getTabID() {
        return "00"+levelID;
    }




}
