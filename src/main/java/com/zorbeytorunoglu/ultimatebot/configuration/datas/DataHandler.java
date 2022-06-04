package com.zorbeytorunoglu.ultimatebot.configuration.datas;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.ConfigurationProvider;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;

import java.io.IOException;

public class DataHandler {

    private final Resource dataResource;
    private final Configuration configuration;

    public DataHandler(Resource dataResource) {
        this.dataResource=dataResource;

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(dataResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Resource getDataResource() {
        return dataResource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
