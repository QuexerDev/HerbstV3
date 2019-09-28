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


    private FileConfiguration cfg = QuexerAPI.getInstance().getConfig();

    public void setLocation(String name, Location loc)
    {
        cfg.set("Location." + name + ".X", Double.valueOf(loc.getX()));
        cfg.set("Location." + name + ".Y", Double.valueOf(loc.getY()));
        cfg.set("Location." + name + ".Z", Double.valueOf(loc.getZ()));
        cfg.set("Location." + name + ".Yaw", Float.valueOf(loc.getYaw()));
        cfg.set("Location." + name + ".Pitch", Float.valueOf(loc.getPitch()));
        cfg.set("Location." + name + ".World", loc.getWorld().getName());
        QuexerAPI.getInstance().saveConfig();
    }



    public boolean exist(String name)
    {
        if (cfg.get("Location." + name + ".X") == null) {
            return false;
        }
        return true;
    }


    public Location getLocation(String name)
    {
        double x = cfg.getDouble("Location." + name + ".X");
        double y = cfg.getDouble("Location." + name + ".Y");
        double z = cfg.getDouble("Location." + name + ".Z");
        double yaw = cfg.getDouble("Location." + name + ".Yaw");
        double pitch = cfg.getDouble("Location." + name + ".Pitch");
        World w = Bukkit.getWorld("spawn");
        Location loc = new Location(w, x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;
    }
}
