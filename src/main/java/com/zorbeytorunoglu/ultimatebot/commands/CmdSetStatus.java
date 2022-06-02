package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class CmdSetStatus implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdSetStatus(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;
        if (!bot.getPermissionHandler().hasPermission("setstatus", Objects.requireNonNull(event.getMember()))) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }
        String[] args=event.getMessage().getContentRaw().split(" ");
        if (args.length!=2) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getSetStatusUsageTitle(),messages.getSetStatusUsage().replace("%command%",getCommand()),messages.getSetStatusUsageColor()
            )).queue();
            return;
        }

        OnlineStatus status = OnlineStatus.fromKey(args[1]);
        if (status==OnlineStatus.UNKNOWN) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(messages.getUnknownStatusTitle(),messages.getUnknownStatus(),messages.getUnknownStatusColor())).queue();
        } else {
            event.getJDA().getPresence().setStatus(status);
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(messages.getStatusSetTitle(),messages.getStatusSet(),messages.getStatusSetColor())).queue();
        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getSetStatus();
    }

}
