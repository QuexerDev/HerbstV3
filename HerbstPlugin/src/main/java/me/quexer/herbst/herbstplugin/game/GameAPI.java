package me.quexer.herbst.herbstplugin.game;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;
import me.quexer.herbst.herbstplugin.HerbstPlugin;

public class GameAPI {

    private HerbstPlugin plugin;

    private GameState[] gameStates;
    private GameState currentGameState;



    private String map;
    private int maxPlayers;


    public GameAPI(HerbstPlugin plugin, String map, int maxPlayers, GameState lobbyState, GameState ingameState, GameState endState) {
        this.plugin = plugin;
        this.map = map;
        this.maxPlayers = maxPlayers;

        gameStates = new GameState[3];

        gameStates[GameState.LOBBY] = lobbyState;
        gameStates[GameState.INGAME] = ingameState;
        gameStates[GameState.END] = endState;
        CloudServer.getInstance().setMotd(map);
        CloudServer.getInstance().setServerState(ServerState.LOBBY);
        CloudServer.getInstance().setMaxPlayers(maxPlayers);
        CloudServer.getInstance().update();
    }



    public void setIngame() {
        CloudServer.getInstance().setServerState(ServerState.INGAME);
        CloudServer.getInstance().changeToIngame();
        CloudServer.getInstance().update();
    }


    public void setState(int gameState) {
        if(currentGameState != null)
            currentGameState.stop();
        currentGameState = gameStates[gameState];
        if(currentGameState != null)
            currentGameState.start();
    }

    public void stopCurrent() {
        if(currentGameState != null) {
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }



    public String getMap() {
        return map;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
