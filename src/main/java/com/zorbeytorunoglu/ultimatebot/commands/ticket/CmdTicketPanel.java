package com.zorbeytorunoglu.ultimatebot.commands.ticket;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketButton;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketPanel;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.TicketUtils;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.Collection;

public class CmdTicketPanel implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdTicketPanel(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("ticketpanel",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length!=2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getTicketPanelUsageTitle(),
                    messages.getTicketPanelUsage().replace("%command%",getCommand()),
                    messages.getTicketPanelUsageColor()
            )).queue();
            return;
        }

        TicketPanel ticketPanel= TicketUtils.getPanel(args[1], bot.getTicketHandler());

        if (ticketPanel==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getTicketPanelNotFoundTitle(),
                    messages.getTicketPanelNotFound(),
                    messages.getTicketPanelNotFoundColor()
            )).queue();
            return;
        }

        MessageBuilder messageBuilder=new MessageBuilder();

        messageBuilder.setEmbeds(ticketPanel.getTicketEmbed());

        ArrayList<Button> buttons=new ArrayList<>();
        ticketPanel.getTicketButtons().forEach(button -> buttons.add(button.getButton()));

        event.getTextChannel().sendMessage(messageBuilder.build()).setActionRow(buttons).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+
                bot.getCommandsHandler().getCommands().getTicketpanel();
    }
}
