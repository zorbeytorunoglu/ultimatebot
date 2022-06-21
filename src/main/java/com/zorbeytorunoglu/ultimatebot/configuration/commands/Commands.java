package com.zorbeytorunoglu.ultimatebot.configuration.commands;

public class Commands {

    private String prefix;
    private String sendMessage;
    private String setPrefix;
    private String setStatus;
    private String givePermission;
    private String takePermission;
    private String kick;
    private String ban;
    private String unban;
    private String ping;
    private String announce;
    private String mute;
    private String unmute;
    private String purge;
    private String slowmode;
    private String warn;
    private String ticketpanel;
    private String add;
    private String remove;

    public Commands() {}

    public String getAdd() { return add; }
    public String getRemove() { return remove; }
    public String getTicketpanel() {
        return ticketpanel;
    }

    public String getWarn() {
        return warn;
    }

    public String getSlowmode() {
        return slowmode;
    }

    public String getPurge() {
        return purge;
    }

    public String getUnmute() {
        return unmute;
    }

    public String getMute() {
        return mute;
    }

    public String getAnnounce() {
        return announce;
    }

    public String getUnban() {
        return unban;
    }

    public String getPing() {
        return ping;
    }

    public String getKick() {
        return kick;
    }

    public String getBan() {
        return ban;
    }

    public String getGivePermission() {
        return givePermission;
    }

    public String getTakePermission() {
        return takePermission;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSetPrefix() {
        return setPrefix;
    }

    public String getSetStatus() {
        return setStatus;
    }

}
