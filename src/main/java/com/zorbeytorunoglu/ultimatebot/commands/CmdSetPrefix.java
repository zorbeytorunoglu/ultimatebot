package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdSetPrefix implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdSetPrefix(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;

        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("setprefix",event.getMember())) {
            event.getTextChannel().sendMessageEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
        }

        if (args.length!=2) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getSetPrefixUsageTitle(),messages.getSetPrefixUsage().replace("%command%", getCommand()),messages.getSetPrefixUsageColor())).queue();
            return;
        }

        bot.getCommandsHandler().getCommands().setPrefix(args[1]);
        event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                messages.getPrefixSetTitle(),messages.getPrefixSet().replace("%prefix%",args[1]),messages.getPrefixSetColor()
        )).queue();

        return;

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getSetPrefix();
    }
}
