package me.quexer.herbst.herbstbungee.enums;

public enum FriendOption {

    JUMP("§eJumpen"),
    MSG("§eNachrichten senden"),
    REQUEST("§eAnfragen erhalten"),
    SERVER("§eServer sehen");

    private String name;

    FriendOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
