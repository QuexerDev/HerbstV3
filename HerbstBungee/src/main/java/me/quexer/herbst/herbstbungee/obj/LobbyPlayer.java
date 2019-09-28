package me.quexer.herbst.herbstbungee.obj;

import me.quexer.herbst.herbstbungee.enums.EnumExtra;

import java.util.List;

public class LobbyPlayer {

    private List<EnumExtra> extraTypes;
    private long dailyReward;

    public long getDailyReward() {
        return dailyReward;
    }

    public void setDailyReward(long dailyReward) {
        this.dailyReward = dailyReward;
    }

    public List<EnumExtra> getExtraTypes() {
        return extraTypes;
    }

    public void setExtraTypes(List<EnumExtra> extraTypes) {
        this.extraTypes = extraTypes;
    }
}
