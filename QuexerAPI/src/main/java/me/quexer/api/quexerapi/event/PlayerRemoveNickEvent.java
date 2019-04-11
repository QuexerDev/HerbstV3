package me.quexer.api.quexerapi.event;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerRemoveNickEvent extends Event {

    public static HandlerList list = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return list;
    }
    private Player p;
    private List<String> nicknames;
    private HashMap<UUID, GameProfile> nickedPlayers;

    public PlayerRemoveNickEvent(Player p, List<String> nicknames, HashMap<UUID, GameProfile> nickedPlayers) {
        this.nickedPlayers = nickedPlayers;
        this.nicknames = nicknames;
        this.p = p;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    public Player getPlayer() {
        return p;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public HashMap<UUID, GameProfile> getNickedPlayers() {
        return nickedPlayers;
    }

    public static HandlerList getList() {
        return list;
    }

}