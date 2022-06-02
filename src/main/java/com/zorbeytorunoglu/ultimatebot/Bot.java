package com.zorbeytorunoglu.ultimatebot;

import com.zorbeytorunoglu.ultimatebot.configuration.commands.CommandsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.MessageHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.settings.SettingsHandler;
import com.zorbeytorunoglu.ultimatebot.permissions.PermissionHandler;
import net.dv8tion.jda.api.JDA;

public class Bot {

    private final SettingsHandler settingsHandler;
    private final PermissionHandler permissionHandler;
    private final MessageHandler messageHandler;
    private final CommandsHandler commandsHandler;
    private JDA jda;

    public Bot(SettingsHandler settingsHandler, PermissionHandler permissionHandler, MessageHandler messageHandler, CommandsHandler commandsHandler) {
        this.settingsHandler=settingsHandler;
        this.permissionHandler=permissionHandler;
        this.messageHandler=messageHandler;
        this.commandsHandler=commandsHandler;
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public JDA getJda() {
        return jda;
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public CommandsHandler getCommandsHandler() {
        return commandsHandler;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

}
