package com.zorbeytorunoglu.ultimatebot.settings;

public class Settings {

    private final String token;
    private final String activity;
    private final String activityLabel;

    public Settings(String token, String activity, String activityLabel) {
        this.token=token;
        this.activity=activity;
        this.activityLabel=activityLabel;
    }

    public String getActivity() {
        return activity;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public String getToken() {
        return token;
    }

}
