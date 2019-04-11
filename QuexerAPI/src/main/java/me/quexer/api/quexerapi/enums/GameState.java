package me.quexer.api.quexerapi.enums;

public enum GameState {

    LOBBY ("§aLobby"),
    INGAME ("§cIngame"),
    RESTART ("§4Restart");

    private String name;

    private GameState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
