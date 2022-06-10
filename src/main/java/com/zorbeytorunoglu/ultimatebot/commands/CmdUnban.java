package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdUnban implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdUnban(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("unban", event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length==1) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getUnbanUsageTitle(),messages.getUnbanUsage().replace("%command%",getCommand()), messages.getUnbanUsageColor()
            )).queue();
            return;
        }

        Guild.Ban ban =event.getGuild().retrieveBanList().stream().filter(banPredicate ->
                banPredicate.getUser().getId().equals(args[1]) || banPredicate.getUser().getName().equals(args[1])).findFirst().orElse(null);

        if (ban==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getNotBannedTitle(),messages.getNotBanned(),messages.getNotBannedColor()
            )).queue();
            return;
        }

        if (!StringUtils.isNumeric(args[1])) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getInvalidNumberTitle(),messages.getInvalidNumber(),messages.getInvalidNumberColor()
            )).queue();
            return;
        }

        event.getGuild().unban(UserSnowflake.fromId(args[1])).queue();

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getUnbannedTitle(),messages.getUnbanned().replace("%member%",ban.getUser().getName()),messages.getUnbannedColor()
        )).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getUnban();
    }
}
