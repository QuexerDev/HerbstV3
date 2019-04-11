package me.quexer.herbst.herbstbungee.manager;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.misc.TextBuilder;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import me.quexer.herbst.herbstbungee.obj.Report;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReportManager {

    private HerbstBungee plugin;

    public ReportManager(HerbstBungee plugin) {
        this.plugin = plugin;
    }

    public boolean isReportet(String uuid) {
        return plugin.getBackendManager().getReportPlayerCache().containsKey(uuid);
    }

    public void report(String senderUUID, String targetUUID, String reason) {

        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));

        if (isReportet(targetUUID)) {
            senderPlayer.sendMessage(plugin.getReportPrefix() + "§cDieser Spieler wurde bereits reportet!");
            return;
        }

        if(plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) == null) {
            senderPlayer.sendMessage(plugin.getReportPrefix()+"§cDieser Spieler ist offline!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getReportPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        Report report = new Report();
        report.setFrom(senderUUID);
        report.setInProgress(false);
        report.setReason(reason);
        report.setUuid(targetUUID);

        plugin.getBackendManager().getReportPlayerCache().put(targetUUID, report);

        List<ProxiedPlayer> team = new ArrayList<>();
        plugin.getProxy().getPlayers().forEach(proxiedPlayer -> {
            if (plugin.getBackendManager().getPlayer(proxiedPlayer.getUniqueId().toString()).getGroup().getLevelID() > 5) {
                team.add(proxiedPlayer);
            }
        });

        team.forEach(proxiedPlayer -> {
            proxiedPlayer.sendMessage(plugin.getReportPrefix());
            proxiedPlayer.sendMessage(plugin.getReportPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
            proxiedPlayer.sendMessage(plugin.getReportPrefix()+target.getGroup().getPrefix()+targetName+" §7wurde von "+sender.getGroup().getPrefix()+senderName+" §7gemeldet§8!");
            proxiedPlayer.sendMessage(plugin.getReportPrefix());
            proxiedPlayer.sendMessage(plugin.getReportPrefix()+"§7Grund§8: §e"+reason);
            new TextBuilder(plugin.getReportPrefix())
                    .addExtra(new TextBuilder("§8[§a§lAnnehmen§8]").setClick("report " + senderName+ " accept").setHover("§7Report annehmen")).sendToPlayer(senderPlayer);
            proxiedPlayer.sendMessage(plugin.getReportPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
            proxiedPlayer.sendMessage(plugin.getReportPrefix());
        });

        senderPlayer.sendMessage(plugin.getReportPrefix() + "§7Dein Report ist §aerfolgreich §7eingegangen§8!");

        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
           plugin.getProxy().getScheduler().schedule(plugin, () -> {
               if(isReportet(targetUUID)) {
                   plugin.getBackendManager().getReportPlayerCache().remove(targetUUID);
               }
           }, 10, TimeUnit.MINUTES);
        });

    }

    public void accept(Report report, String senderUUID) {

        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(report.getUuid());

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(report.getUuid()));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));

        if (!isReportet(report.getUuid())) {
            senderPlayer.sendMessage(plugin.getReportPrefix() + "§cDieser Spieler ist nicht reportet!");
            return;
        }

        if(report.isInProgress()) {
            senderPlayer.sendMessage(plugin.getReportPrefix()+ "§cDieser Report wird bereits bearbeitet!");
            return;
        }

        if(plugin.getProxy().getPlayer(targetName) == null) {
            senderPlayer.sendMessage(plugin.getReportPrefix()+"§cDieser Spieler ist offline!");
            return;
        }
        if(report.getUuid() == senderUUID) {
            senderPlayer.sendMessage(plugin.getReportPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        report.setInProgress(true);

        senderPlayer.sendMessage(plugin.getReportPrefix()+"§7Du hast den Report an "+target.getGroup().getPrefix()+targetName+" §7angenommen");
        senderPlayer.connect(plugin.getProxy().getPlayer(targetName).getServer().getInfo());

        if(plugin.getProxy().getPlayer(UUID.fromString(report.getFrom())) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(report.getFrom())).sendMessage(plugin.getReportPrefix()+"§7Dein Report an "+target.getGroup().getPrefix()+targetName+" §7wird jetzt bearbeitet");
        }

    }

    public void list(ProxiedPlayer player) {
        player.sendMessage(plugin.getReportPrefix()+"§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        if(plugin.getBackendManager().getReportPlayerCache().size() < 1) {
            player.sendMessage(plugin.getReportPrefix()+"§cEs sind keine Reports offen§8!");
        } else {
            plugin.getBackendManager().getReportPlayerCache().forEach((uuid, report) -> {
                BackendPlayer sender = plugin.getBackendManager().getPlayer(report.getFrom());
                BackendPlayer target = plugin.getBackendManager().getPlayer(report.getUuid());

                String senderName = UUIDFetcher.getName(UUID.fromString(report.getFrom()));
                String targetName = UUIDFetcher.getName(UUID.fromString(report.getUuid()));

                new TextBuilder(plugin.getReportPrefix() + sender.getGroup().getPrefix() + senderName + " §e-> "  + report.getReason() + " ")
                        .addExtra(new TextBuilder("§8[§a§lAnnehmen§8]").setClick("report " + senderName + " accept").setHover("§7Report annehmen")).sendToPlayer(player);
            });
        }
        player.sendMessage(plugin.getReportPrefix()+"§8▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }


}
