package com.zorbeytorunoglu.ultimatebot.configuration.settings;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;

public class SettingsContainer {

    public final String token;
    public final String activity;
    public final String activityLabel;

    public SettingsContainer(Configuration configuration) {
        this.token=configuration.getString("token");
        this.activity=configuration.getString("activity");
        this.activityLabel=configuration.getString("activity-label");
    }

}
