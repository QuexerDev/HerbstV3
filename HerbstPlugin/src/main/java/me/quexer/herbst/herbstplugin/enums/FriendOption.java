package me.quexer.herbst.herbstplugin.enums;

public enum FriendOption {

    JUMP("§aJumpen"),
    MSG("§aNachrichten senden"),
    REQUEST("§aAnfragen erhalten"),
    SERVER("§aServer sehen");

    private String name;

    FriendOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
