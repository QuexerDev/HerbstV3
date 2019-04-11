package me.quexer.herbst.herbstbungee.obj;

import java.util.List;

public class BackendPlayer {

    private String uuid;
    private Date date;
    private BackendGroup group;
    private Data data;
    private BanPlayer banPlayer;
    private BanPlayer mutePlayer;
    private FriendPlayer friendPlayer;



    public static class Date {
        private long created_at;
        private long lastLogin;
        private long lastOffline;

        public long getCreated_at() {
            return created_at;
        }

        public long getLastLogin() {
            return lastLogin;
        }

        public long getLastOffline() {
            return lastOffline;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public void setLastLogin(long lastLogin) {
            this.lastLogin = lastLogin;
        }

        public void setLastOffline(long lastOffline) {
            this.lastOffline = lastOffline;
        }
    }

    public static class BanPlayer {

        private String uuid;
        private boolean isPunished;
        private String reason;
        private long end;
        private long punished_at;
        private int banPoints;
        private List<String> history;
        private String punished_from;

        public boolean isPunished() {
            return isPunished;
        }

        public String getReason() {
            return reason;
        }

        public long getEnd() {
            return end;
        }

        public long getPunished_at() {
            return punished_at;
        }

        public List<String> getHistory() {
            return history;
        }

        public String getPunished_from() {
            return punished_from;
        }

        public void setPunished(boolean punished) {
            isPunished = punished;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public void setPunished_at(long punished_at) {
            this.punished_at = punished_at;
        }

        public void setHistory(List<String> history) {
            this.history = history;
        }

        public void setPunished_from(String punished_from) {
            this.punished_from = punished_from;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getBanPoints() {
            return banPoints;
        }

        public void setBanPoints(int banPoints) {
            this.banPoints = banPoints;
        }
    }

    public static class Data {

        private long coins;
        private long keys;
        private long cpr;
        private long elo;
        private boolean nick;

        public long getCoins() {
            return coins;
        }

        public void setCoins(long coins) {
            this.coins = coins;
        }

        public long getKeys() {
            return keys;
        }

        public void setKeys(long keys) {
            this.keys = keys;
        }

        public long getCpr() {
            return cpr;
        }

        public void setCpr(long cpr) {
            this.cpr = cpr;
        }

        public long getElo() {
            return elo;
        }

        public void setElo(long elo) {
            this.elo = elo;
        }

        public boolean isNick() {
            return nick;
        }

        public void setNick(boolean nick) {
            this.nick = nick;
        }
    }


    public String getUuid() {
        return uuid;
    }

    public Date getDate() {
        return date;
    }

    public BackendGroup getGroup() {
        return group;
    }

    public Data getData() {
        return data;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGroup(BackendGroup group) {
        this.group = group;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public BanPlayer getMutePlayer() {
        return mutePlayer;
    }

    public void setMutePlayer(BanPlayer mutePlayer) {
        this.mutePlayer = mutePlayer;
    }

    public BanPlayer getBanPlayer() {
        return banPlayer;
    }

    public void setBanPlayer(BanPlayer banPlayer) {
        this.banPlayer = banPlayer;
    }

    public FriendPlayer getFriendPlayer() {
        return friendPlayer;
    }

    public void setFriendPlayer(FriendPlayer friendPlayer) {
        this.friendPlayer = friendPlayer;
    }
}
