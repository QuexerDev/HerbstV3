package me.quexer.herbst.herbstplugin.manager;

import me.quexer.herbst.herbstplugin.HerbstPlugin;

public class FriendManager {

    private HerbstPlugin plugin;

    public FriendManager(HerbstPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean hasFriend(String senderUUID, String targetUUID) {
        return (plugin.getBackendManager().getPlayer(senderUUID).getFriendPlayer().getFriends().contains(targetUUID) &&
                plugin.getBackendManager().getPlayer(targetUUID).getFriendPlayer().getFriends().contains(senderUUID));
    }

    public void sendRequest(String senderUUID, String targetUUID) {
        plugin.getRabbitMQ().publish("request;"+senderUUID+";"+targetUUID, "herbst-bungee");
    }


}
