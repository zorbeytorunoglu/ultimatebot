package com.zorbeytorunoglu.ultimatebot.settings;

import com.google.gson.Gson;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.settings.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SettingsHandler {

    private final Settings settings;
    private final Resource settingsResource;

    public SettingsHandler(Resource settingsResource) {

        this.settingsResource=settingsResource;

        Settings settings1;
        Gson gson = new Gson();

        try (Reader reader=new FileReader(settingsResource.getFile())) {
            settings1 =gson.fromJson(reader, Settings.class);
        } catch (IOException e) {
            settings1 =null;
            e.printStackTrace();
        }

        this.settings = settings1;

    }

    public Settings getSettings() {
        return settings;
    }

    public Resource getSettingsResource() {
        return settingsResource;
    }
}
