package me.quexer.herbst.herbstbungee;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.quexer.herbst.herbstbungee.commands.*;
import me.quexer.herbst.herbstbungee.database.MongoManager;
import me.quexer.herbst.herbstbungee.database.RabbitMQ;
import me.quexer.herbst.herbstbungee.database.RabbitMQConsumer;
import me.quexer.herbst.herbstbungee.listeners.PlayerChatListener;
import me.quexer.herbst.herbstbungee.listeners.PlayerConnectListener;
import me.quexer.herbst.herbstbungee.manager.*;
import me.quexer.herbst.herbstbungee.misc.AsyncTask;
import net.md_5.bungee.api.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class HerbstBungee extends Plugin {

    private MongoManager mongoManager;
    private static HerbstBungee instance;
    private String prefix;
    private String friendPrefix;
    private String reportPrefix;
    private RabbitMQ rabbitMQ;
    private RabbitMQConsumer rabbitMQConsumer;
    private boolean tabOnThisServer = true;
    private boolean nickOnThisServer = true;
    private BackendManager backendManager;
    private BackendPlayerManager backendPlayerManager;
    private FriendManager friendManager;
    private Gson gson;
    private ExecutorService executor;
    private BanManager banManager;
    private MuteManager muteManager;
    private ReportManager reportManager;


    @Override
    public void onEnable() {
        instance = this;
        prefix = "§eHerbst.net §8▎ §7";
        friendPrefix = "§4Freunde §8▎ §7";
        reportPrefix = "§bReport §8▎ §7";
        mongoManager = new MongoManager("localhost", 27017, "herbstt");
        executor = Executors.newCachedThreadPool();
        rabbitMQ = new RabbitMQ("herbst-bungee");
        rabbitMQConsumer = new RabbitMQConsumer("herbst-spigot", this);

        gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(this);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        backendManager = new BackendManager(this);
        backendPlayerManager = new BackendPlayerManager(this);
        friendManager = new FriendManager(this);
        banManager = new BanManager(this);
        muteManager = new MuteManager(this);
        reportManager = new ReportManager(this);

        getProxy().getPluginManager().registerCommand(this, new GroupCMD(this));
        getProxy().getPluginManager().registerCommand(this, new BanCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ReasonsCMD(this));
        getProxy().getPluginManager().registerCommand(this, new CheckCMD(this));
        getProxy().getPluginManager().registerCommand(this, new UnBanCMD(this));
        getProxy().getPluginManager().registerCommand(this, new UnMuteCMD(this));
        getProxy().getPluginManager().registerCommand(this, new FriendCMD(this));
        getProxy().getPluginManager().registerCommand(this, new MsgCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ReportCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ReportsCMD(this));

        getProxy().getPluginManager().registerListener(this, new PlayerConnectListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerChatListener(this));


           getProxy().getScheduler().schedule(this, () -> {
               if(backendManager.getReportPlayerCache().size() > 0) {
                   getProxy().getPlayers().forEach(player -> {
                       if(backendManager.getPlayer(player.getUniqueId().toString()).getGroup().getLevelID() > 8) {
                           player.sendMessage(reportPrefix+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
                           player.sendMessage(reportPrefix+"§cEs sind noch §e"+backendManager.getReportPlayerCache().size()+" §cReport(s) offen!");
                           player.sendMessage(reportPrefix+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
                       }
                   });
               }
           }, 0, 1, TimeUnit.MINUTES);

    }

    public String getDate(long millis) {
        if(millis == -1) {
            return "§4§lPERMANENT";
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date now = new Date();
            now.setTime(millis);
            return sdfDate.format(now);
        }
    }

    public BackendManager getBackendManager() {
        return backendManager;
    }

    public static HerbstBungee getInstance() {
        return instance;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public RabbitMQ getRabbitMQ() {
        return rabbitMQ;
    }

    public RabbitMQConsumer getRabbitMQConsumer() {
        return rabbitMQConsumer;
    }

    public boolean isTabOnThisServer() {
        return tabOnThisServer;
    }

    public boolean isNickOnThisServer() {
        return nickOnThisServer;
    }

    public BackendPlayerManager getBackendPlayerManager() {
        return backendPlayerManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public Gson getGson() {
        return gson;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public String getFriendPrefix() {
        return friendPrefix;
    }

    public String getReportPrefix() {
        return reportPrefix;
    }
}
