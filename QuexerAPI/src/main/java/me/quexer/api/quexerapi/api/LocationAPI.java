package me.quexer.api.quexerapi.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.quexer.api.quexerapi.QuexerAPI;
import me.quexer.api.quexerapi.misc.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocationAPI {


    Document locations;
    File file = new File(QuexerAPI.getInstance().getDataFolder(), "locations.json");

    public LocationAPI() {
        if(file.exists()) {
            locations = Document.loadDocument(file);
        } else {
            locations = new Document("locations", new Document("locations"));
            locations.saveAsConfig(file);
        }
    }

    public void setLocation(String name, Location loc)
    {
       /* cfg.set("Location." + name + ".X", loc.getX());
        cfg.set("Location." + name + ".Y", loc.getY());
        cfg.set("Location." + name + ".Z", loc.getZ());
        cfg.set("Location." + name + ".Yaw", loc.getYaw());
        cfg.set("Location." + name + ".Pitch", loc.getPitch());
        cfg.set("Location." + name + ".World", loc.getWorld().getName());
        QuexerAPI.getInstance().saveConfig();
        */
       Document jsonObject = new Document();
       jsonObject.append("x", loc.getX());
       jsonObject.append("y", loc.getY());
       jsonObject.append("yaw", loc.getYaw());
       jsonObject.append("pitch", loc.getPitch());
       jsonObject.append("x", loc.getX());
       jsonObject.append("world", loc.getWorld().getName());
       locations.getDocument("locations").append(name,jsonObject);
        locations.saveAsConfig(file);
    }


    public boolean exist(String name) {
        return locations.getDocument(name) != null;
    }

    public Location getLocation(String name)
    {
        Document document = locations.getDocument(name);
        double x = document.getDouble("x");
        double y = document.getDouble("y");
        double z = document.getDouble("z");
        double yaw = document.getDouble("yaw");
        double pitch = document.getDouble("pitch");
        World w = Bukkit.getWorld(document.getString("world"));
        Location loc = new Location(w, x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;
    }
}
