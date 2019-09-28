package me.quexer.herbst.herbstplugin.obj;

public class Data {

    private long coins;
    private long keys;
    private long cpr;
    private long elo;
    private boolean nick;
    private LobbyPlayer lobbyPlayer;

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public long getKeys() {
        return keys;
    }

    public void setKeys(long keys) {
        this.keys = keys;
    }

    public long getCpr() {
        return cpr;
    }

    public void setCpr(long cpr) {
        this.cpr = cpr;
    }

    public long getElo() {
        return elo;
    }

    public void setElo(long elo) {
        this.elo = elo;
    }

    public boolean isNick() {
        return nick;
    }

    public void setNick(boolean nick) {
        this.nick = nick;
    }

    public LobbyPlayer getLobbyPlayer() {
        return lobbyPlayer;
    }

    public void setLobbyPlayer(LobbyPlayer lobbyPlayer) {
        this.lobbyPlayer = lobbyPlayer;
    }
}
