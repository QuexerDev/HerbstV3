package me.quexer.herbst.herbstbungee.manager;

import me.quexer.herbst.herbstbungee.HerbstBungee;
import me.quexer.herbst.herbstbungee.enums.FriendOption;
import me.quexer.herbst.herbstbungee.misc.AsyncTask;
import me.quexer.herbst.herbstbungee.misc.TextBuilder;
import me.quexer.herbst.herbstbungee.misc.uuid.UUIDFetcher;
import me.quexer.herbst.herbstbungee.obj.BackendPlayer;
import me.quexer.herbst.herbstbungee.obj.FriendPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FriendManager {

    private HerbstBungee plugin;

    public FriendManager(HerbstBungee plugin) {
        this.plugin = plugin;
    }

    public boolean hasFriend(String senderUUID, String targetUUID) {
        return (plugin.getBackendManager().getPlayer(senderUUID).getFriendPlayer().getFriends().contains(targetUUID) &&
                plugin.getBackendManager().getPlayer(targetUUID).getFriendPlayer().getFriends().contains(senderUUID));
    }

    public void sendRequest(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist bereits mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if (target.getFriendPlayer().getRequests().contains(senderUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu hast diesem Spieler bereits eine Anfrage gesendet!");
            return;
        }
        if (sender.getFriendPlayer().getRequests().contains(targetUUID)) {
            acceptFriend(senderUUID, targetUUID);
            return;
        }
        if(target.getFriendPlayer().getSettings().get(FriendOption.REQUEST) == false) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler hat Anfragen deaktiviert");
            return;
        }

        if(target.getFriendPlayer().getBlocked().contains(senderUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler hat dich blockiert");
            return;
        }

        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        new AsyncTask(() -> {
            target.getFriendPlayer().getRequests().add(senderUUID);
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du hast dem Spieler " + target.getGroup().getPrefix() + targetName + " §7eine Anfrage gesendet");
            if (plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) != null) {
                plugin.getProxy().getPlayer(UUID.fromString(targetUUID)).sendMessage(plugin.getFriendPrefix() + "§7Du hast eine Freundschaftsanfrage von " + target.getGroup().getPrefix() + targetName + " §7erhalten");
                new TextBuilder(plugin.getFriendPrefix())
                        .addExtra(new TextBuilder("§8[§aAnnehmen").setClick("friend accept " + senderName).setHover("§7Freundschaftsanfrage §aakzeptieren"))
                        .addExtra(new TextBuilder(" §8| "))
                        .addExtra(new TextBuilder("§cAblehnen§8]").setClick("friend deny " + senderName).setHover("§7Freundschaftsanfrage §cablehnen")).sendToPlayer(plugin.getProxy().getPlayer(UUID.fromString(targetUUID)));
            }
        });
    }

    public void acceptFriend(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist bereits mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if (!sender.getFriendPlayer().getRequests().contains(targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu hast keine Anfrage von diesem Spieler erhalten!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }



        sender.getFriendPlayer().getRequests().remove(targetUUID);
        if (target.getFriendPlayer().getRequests().contains(senderUUID)) {
            target.getFriendPlayer().getRequests().remove(senderUUID);
        }

        sender.getFriendPlayer().getFriends().add(targetUUID);
        target.getFriendPlayer().getFriends().add(senderUUID);

        if (plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(targetUUID)).sendMessage(plugin.getFriendPrefix() + target.getGroup().getPrefix() + targetName + " §7hat deine Freundschaftsanfrage §aakzeptiert");
        }
        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du bist nun mit " + target.getGroup().getPrefix() + targetName + " §7befreundet");

    }

    public void denyFriend(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist bereits mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if (!sender.getFriendPlayer().getRequests().contains(targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu hast keine Anfrage von diesem Spieler erhalten!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        sender.getFriendPlayer().getRequests().remove(targetUUID);
        if (target.getFriendPlayer().getRequests().contains(senderUUID)) {
            target.getFriendPlayer().getRequests().remove(senderUUID);
        }

        if (plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(targetUUID)).sendMessage(plugin.getFriendPrefix() + target.getGroup().getPrefix() + targetName + " §7hat deine Freundschaftsanfrage §cabgelehnt");
        }
        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du hast die Anfrage von " + target.getGroup().getPrefix() + targetName + " §7abgelehnt");
        new TextBuilder(plugin.getFriendPrefix())
                .addExtra(new TextBuilder("§8[§c§lSpieler Blockieren§8]").setClick("friend block " + senderName).setHover("§7Spieler blockieren")).sendToPlayer(senderPlayer);


    }

    public void blockPlayer(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (sender.getFriendPlayer().getBlocked().contains(targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu hast diesen Spieler bereits geblockt!");
            new TextBuilder(plugin.getFriendPrefix())
                    .addExtra(new TextBuilder("§8[§a§lSpieler Freigeben§8]").setClick("friend release " + senderName).setHover("§7Spieler freigeben")).sendToPlayer(senderPlayer);
            return;
        }
        if (hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu kannst keine Spieler blocken, die mit dir befreundet sind!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        sender.getFriendPlayer().getBlocked().add(senderUUID);
        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du hast den Spieler " + target.getGroup().getPrefix() + targetName + " §7blockiert");

        if (plugin.getProxy().getPlayer(UUID.fromString(senderUUID)) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(senderUUID)).sendMessage(plugin.getFriendPrefix() + sender.getGroup().getPrefix() + senderName + " §7hat dich blockiert");
        }

    }

    public void releasePlayer(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (!sender.getFriendPlayer().getBlocked().contains(targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu hast diesen Spieler nicht blockiert!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        sender.getFriendPlayer().getBlocked().remove(senderUUID);
        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du hast den Spieler " + target.getGroup().getPrefix() + targetName + " §7freigegeben");

        if (plugin.getProxy().getPlayer(UUID.fromString(senderUUID)) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(senderUUID)).sendMessage(plugin.getFriendPrefix() + sender.getGroup().getPrefix() + senderName + " §7hat dich freigegeben");
        }

    }

    public void changeSetting(String senderUUID, String setting) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        FriendOption option;
        try {
            option = FriendOption.valueOf(setting.toUpperCase());
        } catch (IllegalArgumentException ex) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDiese Einstellung existiert nicht!");
            return;
        }


        if (sender.getFriendPlayer().getSettings().get(option) == false)
            sender.getFriendPlayer().getSettings().put(option, true);
        else
            sender.getFriendPlayer().getSettings().put(option, false);

        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du hast die Einstellung§8: " + option.getName() + " " + (sender.getFriendPlayer().getSettings().get(option) ? "§aAktiviert" : "§cDeaktiviert"));
    }

    public void removeFriend(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (!hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist nicht mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        sender.getFriendPlayer().getFriends().remove(targetUUID);
        target.getFriendPlayer().getFriends().remove(senderUUID);
        senderPlayer.sendMessage(plugin.getFriendPrefix() + "§7Du bist jetzt nicht mehr mit " + target.getGroup().getPrefix() + targetName + " §7befreundet");

        if (plugin.getProxy().getPlayer(UUID.fromString(senderUUID)) != null) {
            plugin.getProxy().getPlayer(UUID.fromString(senderUUID)).sendMessage(plugin.getFriendPrefix() + sender.getGroup().getPrefix() + senderName + " §7hat die Freundschaft mit dir beendet");
        }

    }

    public void jump(String senderUUID, String targetUUID) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (!hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist nicht mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if(target.getFriendPlayer().getSettings().get(FriendOption.JUMP) == false) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler hat das Jumpen deaktiviert!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }
        if(plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler ist offline!");
            return;
        }

        senderPlayer.connect(plugin.getProxy().getPlayer(UUID.fromString(targetUUID)).getServer().getInfo());
        senderPlayer.sendMessage(plugin.getFriendPrefix()+"§7Du wurdest auf den Server von "+target.getGroup().getPrefix()+targetName+" §7gesendet");

    }

    public void msg(String senderUUID, String targetUUID, String msg) {
        BackendPlayer sender = plugin.getBackendManager().getPlayer(senderUUID);
        BackendPlayer target = plugin.getBackendManager().getPlayer(targetUUID);

        String senderName = UUIDFetcher.getName(UUID.fromString(senderUUID));
        String targetName = UUIDFetcher.getName(UUID.fromString(targetUUID));

        ProxiedPlayer senderPlayer = plugin.getProxy().getPlayer(UUID.fromString(senderUUID));
        if (!hasFriend(senderUUID, targetUUID)) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDu bist nicht mit " + target.getGroup().getPrefix() + targetName + " §cbefreundet!");
            return;
        }
        if (targetName == null || targetUUID == null || target == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix() + "§cDieser Spieler existiert nicht!");
            return;
        }
        if(target.getFriendPlayer().getSettings().get(FriendOption.MSG) == false) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler hat private Nachrichten deaktiviert!");
            return;
        }
        if(plugin.getProxy().getPlayer(UUID.fromString(targetUUID)) == null) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDieser Spieler ist offline!");
            return;
        }
        if(targetUUID == senderUUID) {
            senderPlayer.sendMessage(plugin.getFriendPrefix()+"§cDu kannst nicht mit dir selber interagieren!");
            return;
        }

        senderPlayer.sendMessage(plugin.getFriendPrefix()+"§e"+senderName+" -> Dir§8: §7"+msg);
        senderPlayer.sendMessage(plugin.getFriendPrefix()+"§eDu -> "+targetName+"§8: §7"+msg);

    }

    public HashMap<String, Document> getFriendForRabbit(String uuid) {
        FriendPlayer friendPlayer = plugin.getBackendManager().getPlayer(uuid).getFriendPlayer();
        HashMap<String, Document> data = new HashMap<>();
        return null;



    }


}
