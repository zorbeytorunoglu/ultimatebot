package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdSave implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdSave(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;

        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("save", event.getMember())) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }



    }

    @Override
    public String getCommand() {
        return null;
    }
}
