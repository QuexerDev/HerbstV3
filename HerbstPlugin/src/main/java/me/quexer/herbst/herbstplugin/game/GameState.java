package me.quexer.herbst.herbstplugin.game;

public abstract class GameState {


    public static final int LOBBY = 0,
                            INGAME = 1,
                            END = 2;
    private String name;

    public abstract void start();
    public abstract void stop();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
