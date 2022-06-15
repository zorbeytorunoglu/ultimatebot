package com.zorbeytorunoglu.ultimatebot.commands.moderation;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class CmdSlowmode implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdSlowmode(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("slowmode", Objects.requireNonNull(event.getMember()))) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }

        if (args.length>2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getSlowmodeUsageTitle(),
                    messages.getSlowmodeUsage().replace("%command%",getCommand()),
                    messages.getSlowmodeUsageColor()
            )).queue();
            return;
        }

        if (args.length==1) {
            if (event.getTextChannel().getSlowmode()!=0) {
                event.getTextChannel().getManager().setSlowmode(0).queue();
                event.getMessage().delete().queue();
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getSlowmodeDisabledTitle(),
                        messages.getSlowmodeDisabled(),
                        messages.getSlowmodeDisabledColor()
                )).queue();
            } else {
                event.getTextChannel().getManager().setSlowmode(bot.getSettingsHandler().getSettings().getDefaultSlowmode()*60).queue();
                event.getMessage().delete().queue();
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getSlowmodeEnabledTitle(),
                        messages.getSlowmodeEnabled(),
                        messages.getSlowmodeEnabledColor()
                )).queue();
            }
            return;
        }

        if (args.length==2) {
            int minutes;

            try {
                minutes=Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getInvalidNumberTitle(),
                        messages.getInvalidNumber(),
                        messages.getInvalidNumberColor()
                )).queue();
                return;
            }

            if (minutes<0) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getGreaterThanZeroTitle(),
                        messages.getGreaterThanZero(),
                        messages.getGreaterThanZeroColor()
                )).queue();
                return;
            }

            if (minutes>bot.getSettingsHandler().getSettings().getMaxSlowmode()) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getSlowmodeMinutesTooBigTitle(),
                        messages.getSlowmodeMinutesTooBig().replace("%max%",bot.getSettingsHandler().getSettings().getMaxSlowmode()+""),
                        messages.getSlowmodeMinutesTooBigColor()
                )).queue();
                return;
            }

            event.getTextChannel().getManager().setSlowmode(minutes*60).queue();

            event.getMessage().delete().queue();

            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getSlowmodeEnabledTitle(),
                    messages.getSlowmodeEnabled(),
                    messages.getSlowmodeEnabledColor()
            )).queue();
        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getSlowmode();
    }
}
