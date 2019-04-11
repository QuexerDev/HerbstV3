package me.quexer.herbst.herbstbungee.obj;

import java.util.List;

public class Report {

    private String uuid;
    private String from;
    private boolean inProgress;
    private String reason;


    public String getUuid() {
        return uuid;
    }

    public String getFrom() {
        return from;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public String getReason() {
        return reason;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
