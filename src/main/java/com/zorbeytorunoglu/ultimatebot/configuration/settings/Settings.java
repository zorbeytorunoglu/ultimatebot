package com.zorbeytorunoglu.ultimatebot.configuration.settings;

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

}
