package com.zorbeytorunoglu.ultimatebot.configuration.commands;

import com.google.gson.Gson;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CommandsHandler {

    private final Commands commands;
    private final Resource commandsResource;

    public CommandsHandler(Resource commandsResource) {

        this.commandsResource=commandsResource;

        Gson gson = new Gson();

        Commands commands1;

        try (Reader reader=new FileReader(commandsResource.getFile())) {
            commands1=gson.fromJson(reader,Commands.class);
        } catch (IOException e) {{
            commands1=null;
            e.printStackTrace();
        }}

        this.commands=commands1;

    }

    public Commands getCommands() {
        return commands;
    }

    public Resource getCommandsResource() {
        return commandsResource;
    }

}
