package com.zorbeytorunoglu.ultimatebot.listeners;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketButton;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.TicketUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Ticket extends ListenerAdapter {

    private final Bot bot;
    private final Messages messages;

    public Ticket(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if (!event.isFromGuild()) return;

        TicketButton ticketButton= TicketUtils.getTicketButton(event.getButton(),bot.getTicketHandler());

        if (ticketButton==null) return;

        if (!bot.getPermissionHandler().hasPermission("createticket",event.getMember())) {
            event.replyEmbeds(BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())).setEphemeral(true).queue();
            return;
        }

        if (TicketUtils.getTicketCount(event.getMember().getId())>=bot.getSettingsHandler().getSettings().getMemberTicketLimit()) {

            event.replyEmbeds(BotUtils.getEmbed(
                    messages.getMaxTicketErrorTitle(),
                    messages.getMaxTicketError().replace("%max%", bot.getSettingsHandler().getSettings().getMemberTicketLimit()+""),
                    messages.getMaxTicketErrorColor()
            )).setEphemeral(true).queue();
            return;

        }

        TextChannel createdTicket=TicketUtils.createTicket(ticketButton, event.getMember());

        createdTicket.sendMessageEmbeds(ticketButton.getEmbedBuilder().setDescription(ticketButton.getEmbedDescription()
                .replace("%member%", event.getMember().getAsMention())).build()).queue();

        event.replyEmbeds(BotUtils.getEmbed(
                messages.getTicketCreatedTitle(),
                messages.getTicketCreated().replace("%ticket%", createdTicket.getAsMention()
                ).replace("%member%",event.getMember().getAsMention()),
                messages.getTicketCreatedColor()
        )).setEphemeral(true).queueAfter(20L, TimeUnit.MILLISECONDS);

    }
}
