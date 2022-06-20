package com.zorbeytorunoglu.ultimatebot.configuration.data;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class Warn {

    private static HashMap<String, Integer> warns=new HashMap<>();

    public static HashMap<String, Integer> getWarns() {
        return warns;
    }

    public static void load(DataHandler dataHandler) {
        if (!dataHandler.getDataResource().getFile().exists()) return;
        if (dataHandler.getConfiguration().getSection("warns")==null) return;

        Collection<String> collection=dataHandler.getConfiguration().getSection("warns").getKeys();

        if (collection.isEmpty()) return;

        for (String id:collection) {
            warns.put(id,dataHandler.getConfiguration().getInt("warns."+id));
            dataHandler.getConfiguration().set("warns."+id, null);
        }

        try {
            dataHandler.getYamlConfiguration().save(dataHandler.getConfiguration(),dataHandler.getDataResource().getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void save(DataHandler dataHandler) {
        if (warns.isEmpty()) return;
        for (String id:warns.keySet()) {
            dataHandler.getConfiguration().set("warns."+id, warns.get(id));
        }
        try {
            dataHandler.getYamlConfiguration().save(dataHandler.getConfiguration(), dataHandler.getDataResource().getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
