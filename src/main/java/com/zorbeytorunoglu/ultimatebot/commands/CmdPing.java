package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class CmdPing implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdPing(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;

        if (!bot.getPermissionHandler().hasPermission("ping",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        event.getMessage().replyEmbeds(BotUtils.getEmbed("none","Pong...","GREEN")).queue(message -> {
            long ping = event.getMessage().getTimeCreated().until(message.getTimeCreated(), ChronoUnit.MILLIS);
            message.editMessageEmbeds(BotUtils.getEmbed(messages.getPongTitle(),messages.getPong().replace("%gatewayPing%",event.getJDA().getGatewayPing() + "ms")
                            .replace("%ping%", ping+"ms"),
                    messages.getPongColor())).queue();
        });

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getPing();
    }
}
