package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CmdPurge implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdPurge(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("purge",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length!=2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getPurgeUsageTitle(),
                    messages.getPurgeUsage().replace("%command%", getCommand()),
                    messages.getPurgeUsageColor()
            )).queue();
            return;
        }

        if (!StringUtils.isNumeric(args[1])) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getInvalidNumberTitle(),
                    messages.getInvalidNumber(),
                    messages.getInvalidNumberColor()
            )).queue();
            return;
        }

        int number=Integer.parseInt(args[1]);

        if (number<=0) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getGreaterThanZeroTitle(),
                    messages.getGreaterThanZero(),
                    messages.getGreaterThanZeroColor()
            )).queue();
            return;
        }

        if (number>bot.getSettingsHandler().getSettings().getMaxPurgeNumber()) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getPurgeNumberTooBigTitle(),
                    messages.getPurgeNumberTooBig().replace("%max%", bot.getSettingsHandler().getSettings().getMaxPurgeNumber()+""),
                    messages.getPurgeNumberTooBigColor()
            )).queue();
            return;
        }

        event.getMessage().delete().queue();

        List<Message> messageList = event.getChannel().getHistory().retrievePast(number).complete();

        event.getTextChannel().deleteMessages(messageList).queue();

        event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                messages.getPurgeCompleteTitle(),
                messages.getPurgeComplete().replace("%number%",number+""),
                messages.getPurgeCompleteColor()
        )).queue();
        return;

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getPurge();
    }
}
