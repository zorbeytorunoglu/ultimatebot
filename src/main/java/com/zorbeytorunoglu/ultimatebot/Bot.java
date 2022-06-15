package com.zorbeytorunoglu.ultimatebot;

import com.zorbeytorunoglu.ultimatebot.configuration.commands.CommandsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.datas.DataHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.MessageHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.settings.SettingsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketHandler;
import com.zorbeytorunoglu.ultimatebot.permissions.PermissionHandler;
import net.dv8tion.jda.api.JDA;

public class Bot {
    private final SettingsHandler settingsHandler;
    private final PermissionHandler permissionHandler;
    private final MessageHandler messageHandler;
    private final CommandsHandler commandsHandler;
    private final DataHandler dataHandler;
    private final TicketHandler ticketHandler;
    private JDA jda;

    public Bot(SettingsHandler settingsHandler, PermissionHandler permissionHandler, MessageHandler messageHandler,
               CommandsHandler commandsHandler, DataHandler dataHandler, TicketHandler ticketHandler) {
        this.settingsHandler=settingsHandler;
        this.permissionHandler=permissionHandler;
        this.messageHandler=messageHandler;
        this.commandsHandler=commandsHandler;
        this.dataHandler=dataHandler;
        this.ticketHandler=ticketHandler;
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public TicketHandler getTicketHandler() { return ticketHandler; }

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

    public DataHandler getDataHandler() {
        return dataHandler;
    }
}
