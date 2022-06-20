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
    public Integer getMaxSlowmode() { return settingsContainer.maxSlowmode; }
    public Integer getDefaultSlowmode() { return settingsContainer.defaultSlowmode; }
    public Integer getWarnLimit() { return settingsContainer.warnLimit; }
    public String getWarnAction() { return settingsContainer.warnAction; }
    public Integer getMemberTicketLimit() { return settingsContainer.memberTicketLimit; }

}
