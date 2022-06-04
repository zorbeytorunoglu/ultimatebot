package com.zorbeytorunoglu.ultimatebot.configuration.settings;

import com.google.gson.Gson;
import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.ConfigurationProvider;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SettingsHandler {

    private final Settings settings;
    private final Configuration configuration;
    private final Resource settingsResource;

    public SettingsHandler(Resource settingsResource) {

        this.settingsResource=settingsResource;

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(settingsResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.settings=new Settings(new SettingsContainer(configuration));

    }

    public Settings getSettings() {
        return settings;
    }

    public Resource getSettingsResource() {
        return settingsResource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
