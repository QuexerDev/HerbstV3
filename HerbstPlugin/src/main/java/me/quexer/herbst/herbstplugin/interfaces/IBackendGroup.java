package me.quexer.herbst.herbstplugin.interfaces;

public interface IBackendGroup {

    String getPrefix();
    String getColor();
    int getLevelID();
    int tabID();
    boolean hasPermission(int level);
    String getFullName();
    String getTabID();

}
