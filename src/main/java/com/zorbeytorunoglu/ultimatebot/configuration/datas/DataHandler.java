package com.zorbeytorunoglu.ultimatebot.configuration.datas;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.ConfigurationProvider;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;

import java.io.IOException;

public class DataHandler {

    private final Resource dataResource;
    private final Configuration configuration;
    private final YamlConfiguration yamlConfiguration;

    public DataHandler(Resource dataResource) {
        this.dataResource=dataResource;
        this.yamlConfiguration=new YamlConfiguration();
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

    public YamlConfiguration getYamlConfiguration() { return yamlConfiguration; }

    public void load(Bot bot) {
        Mute.setBot(bot);
        Mute.load(dataResource,configuration);
        Warn.load(this);
    }

    public void save(Bot bot) {
        try {
            Mute.save(yamlConfiguration,configuration,dataResource);
            Warn.save(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
