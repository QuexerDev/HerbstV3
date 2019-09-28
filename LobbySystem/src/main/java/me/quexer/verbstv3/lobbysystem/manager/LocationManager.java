package me.quexer.verbstv3.lobbysystem.manager;

import me.quexer.verbstv3.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationManager {

    private Location spawn;
    private Location survivalGames;
    private Location buildFFA;
    private Location defuse;
    private Location dailyReward;
    private Location dailyRewardNPC;
    private Location cprShop;
    private Location cprShopNPC;
    private Location caseOpening;
    private Location caseOpeningCase;
    private Location keyshop;
    private Location keyshopnpc;
    private LobbySystem plugin;

    public LocationManager(LobbySystem plugin) {
        this.plugin = plugin;
        spawn = getLocation("spawn");
        survivalGames = getLocation("survivalgames");
        buildFFA = getLocation("buildffa");
        defuse = getLocation("defuse");
        dailyReward = getLocation("dailyreward");
        dailyRewardNPC = getLocation("dailyrewardnpc");
        caseOpening = getLocation("caseopening");
        caseOpeningCase = getLocation("caseopeningcase");
        keyshop = getLocation("keyshop");
        keyshopnpc = getLocation("keyshopnpc");
        cprShop = getLocation("cprshop");
        cprShopNPC = getLocation("cprshopnpc");
    }

    public Location getLocation(String name) {
        if(plugin.getQuexerAPI().getLocationAPI().exist(name))
            return plugin.getQuexerAPI().getLocationAPI().getLocation(name);
        else return new Location(Bukkit.getWorld("spawn"), -111, 5, 505);
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getSurvivalGames() {
        return survivalGames;
    }

    public Location getBuildFFA() {
        return buildFFA;
    }

    public Location getDefuse() {
        return defuse;
    }

    public Location getDailyReward() {
        return dailyReward;
    }

    public Location getDailyRewardNPC() {
        return dailyRewardNPC;
    }

    public Location getCaseOpening() {
        return caseOpening;
    }

    public Location getCaseOpeningCase() {
        return caseOpeningCase;
    }

    public Location getKeyshop() {
        return keyshop;
    }

    public Location getKeyshopnpc() {
        return keyshopnpc;
    }

    public Location getCprShop() {
        return cprShop;
    }

    public Location getCprShopNPC() {
        return cprShopNPC;
    }
}
