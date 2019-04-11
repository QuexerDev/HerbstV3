package me.quexer.api.quexerapi.event;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerNickEvent extends Event {

    public static HandlerList list = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return list;
    }
    private Player p;
    private List<String> nicknames;
    private HashMap<UUID, GameProfile> nickedPlayers;
    private String nick;

    public PlayerNickEvent(Player p, List<String> nicknames, HashMap<UUID, GameProfile> nickedPlayers, String nick) {
        this.nickedPlayers = nickedPlayers;
        this.nicknames = nicknames;
        this.p = p;
        this.nick = nick;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    public static HandlerList getList() {
        return list;
    }

    public HashMap<UUID, GameProfile> getNickedPlayers() {
        return nickedPlayers;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public Player getPlayer() {
        return p;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }
}
