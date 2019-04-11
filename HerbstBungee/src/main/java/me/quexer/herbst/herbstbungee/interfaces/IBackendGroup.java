package me.quexer.herbst.herbstbungee.interfaces;

public interface IBackendGroup {

    String getPrefix();
    String getColor();
    int getLevelID();
    int tabID();
    boolean hasPermission(int level);
    String getFullName();
    String getTabID();

}
