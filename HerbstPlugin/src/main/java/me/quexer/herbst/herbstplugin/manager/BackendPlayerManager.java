package me.quexer.herbst.herbstplugin.manager;

import com.mongodb.async.client.ClientSession;
import com.mongodb.client.model.Filters;
import me.quexer.herbst.herbstplugin.HerbstPlugin;
import me.quexer.herbst.herbstplugin.enums.FriendOption;
import me.quexer.herbst.herbstplugin.obj.BackendPlayer;
import me.quexer.herbst.herbstplugin.obj.FriendPlayer;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class BackendPlayerManager {

    private HerbstPlugin plugin;

    public BackendPlayerManager(HerbstPlugin plugin) {
        this.plugin = plugin;
    }

    public void getFromDB(UUID uuid, Consumer<BackendPlayer> consumer) {
        plugin.getMongoManager().getCollection("backendplayer").find(Filters.eq("uuid", uuid.toString())).first((document, throwable) -> {
            if (document == null) {


                if(Bukkit.getPlayer(uuid) != null) {
                    Bukkit.getPlayer(uuid).kickPlayer("§cWegen einem Fehler musst du den Server neu betreten!");
                }

                return;
            } else {
                BackendPlayer user = plugin.getQuexerAPI().getGson().fromJson(document.toJson(), BackendPlayer.class);
                consumer.accept(user);
                if(Bukkit.getPlayer(uuid) != null) {
                    plugin.getQuexerAPI().setMetadata(Bukkit.getPlayer(uuid), "backendplayer", user);
                }
                return;
            }
        });
    }






    public void saveToDB(BackendPlayer backendPlayer) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            Document document = new Document("data", plugin.getQuexerAPI().getGson().fromJson(plugin.getQuexerAPI().getGson().toJson(backendPlayer.getData()), Document.class));
            Document update = plugin.getQuexerAPI().getGson().fromJson(plugin.getQuexerAPI().getGson().toJson(backendPlayer.getData()), Document.class);




            plugin.getMongoManager().getCollection("backendplayer").updateOne(Filters.eq("uuid", backendPlayer.getUuid()), new Document("$set", new Document("data", update)), (result, t) -> {

                //System.out.println(result.toJson());
                System.out.println("Saved to database");
                t.printStackTrace();
            });
        }, 10);



    }

}


