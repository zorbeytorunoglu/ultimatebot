package com.zorbeytorunoglu.ultimatebot.utils;

import com.zorbeytorunoglu.ultimatebot.configuration.data.Ticket;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketButton;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketPanel;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class TicketUtils {

    public static boolean isValidTicketPanelId(String panelId, TicketHandler ticketHandler) {
        if (!ticketHandler.getTicketPanelsResource().getFile().exists()) return false;
        if (!ticketHandler.getConfiguration().getKeys().contains(panelId)) return false;
        return true;
    }

    public static TicketPanel getPanel(String panelId, TicketHandler ticketHandler) {
        if (ticketHandler.getTicketPanels().isEmpty()) return null;
        return ticketHandler.getTicketPanels().stream().filter(ticketPanel ->
                ticketPanel.getPanelId().equals(panelId)).findFirst().orElse(null);
    }

    public static TicketPanel getPanel(Button button, TicketHandler ticketHandler) {
        if (ticketHandler.getTicketPanels().isEmpty()) return null;
        for (TicketPanel ticketPanel:ticketHandler.getTicketPanels()) {
            if (ticketPanel.getTicketButtons().isEmpty()) continue;
            for (TicketButton ticketButton:ticketPanel.getTicketButtons()) {
                if (ticketButton.getButton().equals(button)) return ticketPanel;
            }
        }
        return null;
    }

    public static TicketButton getTicketButton(Button button, TicketHandler ticketHandler) {
        if (ticketHandler.getTicketPanels().isEmpty()) return null;
        for (TicketPanel ticketPanel:ticketHandler.getTicketPanels()) {
            if (ticketPanel.getTicketButtons().isEmpty()) continue;
            for (TicketButton ticketButton:ticketPanel.getTicketButtons()) {
                if (ticketButton.getButton().equals(button)) return ticketButton;
            }
        }
        return null;
    }

    public static TextChannel createTicket(TicketButton ticketButton, Member member) {

        Category category=member.getGuild().getCategoryById(ticketButton.getTicketCategoryId());

        if (category==null) return null;

        TextChannel textChannel=
        member.getGuild().createTextChannel(ticketButton.getTicketNameFormat().replace("%member%",member.getEffectiveName()),category).complete();

        if (!ticketButton.getMemberPermissions().isEmpty()) {
            textChannel.getManager().putPermissionOverride(member,ticketButton.getMemberPermissions(),Collections.EMPTY_LIST)
                    .queue();
        }

        long queue=1;
        for (String role:ticketButton.getTicketRolesToBeAdded()) {
            role=role.replace("-", " ");

            Collection<Permission> allow=ticketButton.getAllowedPermissions().get(role);
            Collection<Permission> deny=ticketButton.getDeniedPermissions().get(role);

            Role roleObj=member.getGuild().getRolesByName(role,false).get(0);

            textChannel.getManager().putRolePermissionOverride(roleObj.getIdLong(),allow,deny)
                    .queueAfter(queue,TimeUnit.SECONDS);

            queue++;

        }

        if (!ticketButton.getTicketPingRoles().isEmpty()) {
            StringBuilder stringBuilder=new StringBuilder();
            for (int i=0; i<ticketButton.getTicketPingRoles().size(); i++) {
                if (i==ticketButton.getTicketPingRoles().size()-1) {
                    stringBuilder.append(member.getGuild().getRolesByName(
                            ticketButton.getTicketPingRoles().get(i), false
                    ).get(0).getAsMention());
                } else {
                    stringBuilder.append(member.getGuild().getRolesByName(
                            ticketButton.getTicketPingRoles().get(i), false
                    ).get(0).getAsMention()+", ");
                }
            }
            textChannel.sendMessage(stringBuilder.toString()).queue();
        }

        Ticket ticket=new Ticket(member,textChannel);

        return textChannel;

    }

    public static Integer getTicketCount(Member member) {
        int count=0;
        if (Ticket.getTickets().isEmpty()) return count;
        for (Ticket ticket:Ticket.getTickets()) {
            if (ticket.getOwner().equals(member.getId())) count++;
        }
        return count;
    }

    public static Integer getTicketCount(String memberId) {
        int count=0;
        if (Ticket.getTickets().isEmpty()) return count;
        for (Ticket ticket:Ticket.getTickets()) {
            if (ticket.getOwner().equals(memberId)) count++;
        }
        return count;
    }

}
