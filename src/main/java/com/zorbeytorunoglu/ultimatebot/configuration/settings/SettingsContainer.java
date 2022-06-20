package com.zorbeytorunoglu.ultimatebot.configuration.settings;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;

public class SettingsContainer {

    public final String token;
    public final String activity;
    public final String activityLabel;
    public final String mutedRoleName;
    public final Integer maxMuteMinute;
    public final Integer maxPurgeNumber;
    public final Integer maxSlowmode;
    public final Integer defaultSlowmode;
    public final Integer warnLimit;
    public final String warnAction;
    public final Integer memberTicketLimit;

    public SettingsContainer(Configuration configuration) {
        this.token=configuration.getString("token");
        this.activity=configuration.getString("activity");
        this.activityLabel=configuration.getString("activity-label");
        this.mutedRoleName=configuration.getString("mute.role-name");
        this.maxMuteMinute=configuration.getInt("mute.max-mute-min");
        this.maxPurgeNumber=configuration.getInt("purge.max-number");
        this.maxSlowmode=configuration.getInt("slowmode.max-minutes");
        this.defaultSlowmode=configuration.getInt("slowmode.default");
        this.warnLimit=configuration.getInt("warns.limit");
        this.warnAction=configuration.getString("warns.action");
        this.memberTicketLimit=configuration.getInt("tickets.member-ticket-limit");
    }
}
