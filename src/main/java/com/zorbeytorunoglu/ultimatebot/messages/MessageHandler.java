package com.zorbeytorunoglu.ultimatebot.messages;

import com.google.gson.Gson;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MessageHandler {

    private final com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages messages;
    private final Resource messagesResource;

    public MessageHandler(Resource messagesResource) {

        this.messagesResource=messagesResource;

        Gson gson = new Gson();

        com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages messages1;

        try (Reader reader=new FileReader(messagesResource.getFile())) {
            messages1=gson.fromJson(reader, com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages.class);
        } catch (IOException e) {{
            messages1=null;
            e.printStackTrace();
        }}

        this.messages=messages1;

    }

    public Messages getMessages() {
        return messages;
    }

    public Resource getMessagesResource() {
        return messagesResource;
    }
}
