package me.quexer.herbst.herbstbungee.obj;


import me.quexer.herbst.herbstbungee.enums.FriendOption;

import java.util.HashMap;
import java.util.List;

public class FriendPlayer {

    private List<String> requests;
    private List<String> friends;
    private List<String> blocked;
    private HashMap<FriendOption, Boolean> settings;

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

    public HashMap<FriendOption, Boolean> getSettings() {
        return settings;
    }

    public void setSettings(HashMap<FriendOption, Boolean> settings) {
        this.settings = settings;
    }
}
