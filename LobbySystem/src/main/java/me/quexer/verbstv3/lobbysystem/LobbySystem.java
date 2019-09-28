package me.quexer.verbstv3.lobbysystem;

import de.robingrether.idisguise.api.DisguiseAPI;
import me.quexer.api.quexerapi.api.NickAPI;
import me.quexer.herbst.herbstplugin.HerbstPlugin;
import me.quexer.verbstv3.lobbysystem.commands.SetSpawnCMD;
import me.quexer.verbstv3.lobbysystem.listeners.MainListeners;
import me.quexer.verbstv3.lobbysystem.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbySystem extends HerbstPlugin implements Listener {

    private static String lobbyPrefix;
    private static LobbySystem instance;

    private SetSpawnCMD setSpawnCMD;
    private MainListeners mainListeners;
    private DailyRewardManager dailyRewardManager;
    private GadgetsManager gadgetsManager;
    private InventoryManager inventoryManager;
    private ItemManager itemManager;
    private KeyShopManager keyShopManager;
    private LocationManager locationManager;
    private ScoreboardManager scoreboardManager;
    private CPRShopManager cprShopManager;

    private DisguiseAPI disguiseAPI;


    @Override
    public void init() {
        instance = this;
        lobbyPrefix = "§eLobby §8▎ §7";

        setNickOnThisServer(false);

        Bukkit.getWorlds().forEach(world -> {
            world.setAmbientSpawnLimit(1);
            world.setAutoSave(false);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(1000);
            world.setPVP(false);
            world.setStorm(false);
            world.setThunderDuration(0);
            world.setThundering(false);
            world.setWeatherDuration(Integer.MAX_VALUE-1);
            world.setGameRuleValue("doMobSpawning", "false");
            world.getEntities().forEach(entity -> entity.remove());
        });

        mainListeners = new MainListeners(this);
        disguiseAPI = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
        gadgetsManager = new GadgetsManager(this);
        locationManager = new LocationManager(this);
        dailyRewardManager = new DailyRewardManager(this);
        scoreboardManager = new ScoreboardManager(this);
        inventoryManager = new InventoryManager(this);
        itemManager = new ItemManager(this);
        keyShopManager = new KeyShopManager(this);
        cprShopManager = new CPRShopManager(this);

        getCommand("setSpawn").setExecutor(new SetSpawnCMD(this));


        locationManager.getCaseOpeningCase().getBlock().setType(Material.CHEST);
        locationManager.getCaseOpeningCase().getBlock().setMetadata("caseopening", new FixedMetadataValue(this, "hallo"));
        Bukkit.getPluginManager().registerEvents(this, this);


    }

    @Override
    public void disable() {

    }
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if(event.getEntityType() == EntityType.VILLAGER || event.getEntityType() == EntityType.PLAYER)
            return;
        else
            event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {

        e.setCancelled(true);

    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);

    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        e.setCancelled(true);
        e.setBuild(false);

    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
        e.setDamage(0);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }
    @EventHandler
    public void onEntity(PlayerInteractAtEntityEvent e) {
        e.setCancelled(true);
    }

    public static String getLobbyPrefix() {
        return lobbyPrefix;
    }

    public SetSpawnCMD getSetSpawnCMD() {
        return setSpawnCMD;
    }

    public MainListeners getMainListeners() {
        return mainListeners;
    }

    public DailyRewardManager getDailyRewardManager() {
        return dailyRewardManager;
    }

    public GadgetsManager getGadgetsManager() {
        return gadgetsManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public KeyShopManager getKeyShopManager() {
        return keyShopManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }


    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }



    public DisguiseAPI getDisguiseAPI() {
        return disguiseAPI;
    }

    public CPRShopManager getCprShopManager() {
        return cprShopManager;
    }
}
