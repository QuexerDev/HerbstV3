package me.quexer.herbst.herbstbungee.manager;

import com.mongodb.client.model.Filters;
import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.enums.FriendOption;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import me.quexer.herbst.herbstbungee.obj.Data;
import me.quexer.herbst.herbstbungee.obj.FriendPlayer;
import me.quexer.herbst.herbstbungee.obj.LobbyPlayer;
import org.bson.Document;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class BackendPlayerManager {

    private HerbstBungee plugin;

    public BackendPlayerManager(HerbstBungee plugin) {
        this.plugin = plugin;
    }

    public void getFromDB(UUID uuid, Consumer<BackendPlayer> consumer) {
        plugin.getMongoManager().getCollection("backendplayer").find(Filters.eq("uuid", uuid.toString())).first((document, throwable) -> {
            if (document == null) {
                BackendPlayer.BanPlayer banPlayer = new BackendPlayer.BanPlayer();
                banPlayer.setEnd(-1);
                banPlayer.setPunished(false);
                banPlayer.setHistory(new ArrayList<>());
                banPlayer.setReason(null);
                banPlayer.setPunished_at(-1);
                banPlayer.setPunished_from("NONE");
                banPlayer.setUuid(uuid.toString());
                banPlayer.setBanPoints(0);

                BackendPlayer.BanPlayer mutePlayer = new BackendPlayer.BanPlayer();
                mutePlayer.setEnd(-1);
                mutePlayer.setPunished(false);
                mutePlayer.setHistory(new ArrayList<>());
                mutePlayer.setReason(null);
                mutePlayer.setPunished_at(-1);
                mutePlayer.setPunished_from("NONE");
                mutePlayer.setUuid(uuid.toString());
                mutePlayer.setBanPoints(0);

                LobbyPlayer lobbyPlayer = new LobbyPlayer();
                lobbyPlayer.setExtraTypes(new ArrayList<>());
                lobbyPlayer.setDailyReward(System.currentTimeMillis());

                Data data = new Data();
                data.setNick(false);
                data.setElo(1000);
                data.setKeys(1);
                data.setCpr(100+ new Random().nextInt(100));
                data.setCoins(1000);
                data.setLobbyPlayer(lobbyPlayer);

                BackendPlayer.Date date = new BackendPlayer.Date();
                date.setCreated_at(System.currentTimeMillis());
                date.setLastLogin(System.currentTimeMillis());
                date.setLastOffline(System.currentTimeMillis());

                FriendPlayer friendPlayer = new FriendPlayer();
                friendPlayer.setBlocked(new ArrayList<>());
                friendPlayer.setFriends(new ArrayList<>());
                friendPlayer.setRequests(new ArrayList<>());
                HashMap<FriendOption, Boolean> options = new HashMap<FriendOption, Boolean>();
                options.put(FriendOption.JUMP, true);
                options.put(FriendOption.MSG, true);
                options.put(FriendOption.REQUEST, true);
                options.put(FriendOption.SERVER, true);
                friendPlayer.setSettings(options);

                BackendPlayer user = new BackendPlayer();
                user.setUuid(uuid.toString());
                user.setGroup(plugin.getBackendManager().getLowestGroup());
                user.setMutePlayer(mutePlayer);
                user.setBanPlayer(banPlayer);
                user.setData(data);
                user.setDate(date);
                user.setFriendPlayer(friendPlayer);





                consumer.accept(user);

                document = plugin.getGson().fromJson(plugin.getGson().toJson(user), Document.class);
                    plugin.getBackendManager().getBackendPlayerCache().put(uuid.toString(), user);
                plugin.getMongoManager().getCollection("backendplayer").insertOne(document, (aVoid, throwable1) -> {

                });

                return;
            } else {
                BackendPlayer user = plugin.getGson().fromJson(document.toJson(), BackendPlayer.class);
                consumer.accept(user);
                    plugin.getBackendManager().getBackendPlayerCache().put(uuid.toString(), user);
                return;
            }
        });
    }






    public BackendPlayer saveToDB(BackendPlayer backendPlayer) {
        Document document = plugin.getGson().fromJson(plugin.getGson().toJson(backendPlayer), Document.class);
        plugin.getMongoManager().getCollection("backendplayer")
                .replaceOne(Filters.eq("uuid", backendPlayer.getUuid()), document, (result, t) -> {
                });
        return backendPlayer;
    }

}


