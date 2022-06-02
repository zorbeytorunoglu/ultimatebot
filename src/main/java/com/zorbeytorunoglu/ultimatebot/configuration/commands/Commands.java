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

    public Commands() {}

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
