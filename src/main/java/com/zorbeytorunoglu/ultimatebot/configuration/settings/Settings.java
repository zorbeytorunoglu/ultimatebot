package com.zorbeytorunoglu.ultimatebot.configuration.settings;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Settings {

    private final SettingsContainer settingsContainer;

    public Settings(SettingsContainer settingsContainer) {
        this.settingsContainer=settingsContainer;
    }

    public String getToken() {
        return settingsContainer.token;
    }
    public String getActivity() {
        return settingsContainer.activity;
    }
    public String getActivityLabel() {
        return settingsContainer.activityLabel;
    }
    public String getMuteRoleName() { return settingsContainer.mutedRoleName; }
    public Integer getMaxMuteMinute() { return settingsContainer.maxMuteMinute; }
    public Integer getMaxPurgeNumber() { return settingsContainer.maxPurgeNumber; }
    public Integer getMaxSlowmode() { return settingsContainer.maxSlowmode; }
    public Integer getDefaultSlowmode() { return settingsContainer.defaultSlowmode; }
    public Integer getWarnLimit() { return settingsContainer.warnLimit; }
    public String getWarnAction() { return settingsContainer.warnAction; }
    public Integer getMemberTicketLimit() { return settingsContainer.memberTicketLimit; }
    public boolean getWelcomeEnabled() { return settingsContainer.welcomeEnabled; }
    public Collection<Permission> getTicketMemberAddPermissions() {
        String[] perms=settingsContainer.ticketAddMemberPerms.split(",");
        if (perms.length==0) return Collections.EMPTY_LIST;
        Collection<Permission> permissionCollection=new ArrayList<>();
        for (String perm:perms) {
            permissionCollection.add(Permission.valueOf(Permission.class,perm));
        }
        return permissionCollection;
    }
    public String getWelcomeEmbedAuthor() { return settingsContainer.welcomeEmbedAuthor; }
    public String getWelcomeEmbedAuthorUrl() { return settingsContainer.welcomeEmbedAuthorUrl; }
    public String getWelcomeEmbedAuthorImgUrl() { return settingsContainer.welcomeEmbedAuthorImgUrl; }
    public String getWelcomeEmbedFooter() { return settingsContainer.welcomeEmbedFooter; }
    public String getWelcomeEmbedFooterImgUrl() { return settingsContainer.welcomeEmbedFooterImgUrl; }
    public String getWelcomeEmbedThumbnail() { return settingsContainer.welcomeEmbedThumbnailUrl; }
    public Collection<MessageEmbed.Field> getWelcomeEmbedFields() { return settingsContainer.welcomeEmbedFields; }
    public Color getWelcomeEmbedColor() { return settingsContainer.welcomeEmbedColor; }
    public String getWelcomeTextChannelId() { return settingsContainer.welcomeTextChannel; }
    public String getWelcomeEmbedDescription() { return settingsContainer.welcomeEmbedDescription; }
    public String getWelcomeEmbedTitle() { return settingsContainer.welcomeEmbedTitle; }

}
