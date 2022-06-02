package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MCPLCommand {

    void execute(Bot bot, MessageReceivedEvent event);

    String getCommand();

}
