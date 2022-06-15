package com.zorbeytorunoglu.ultimatebot.configuration.tickets;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
import java.util.Objects;

public class TicketButton {

    private final String id;
    private String label=null;
    private String emojiId=null;
    private String ticketCategoryId=null;
    private String ticketNameFormat=null;
    private List<String> ticketRolesToBeAdded=null;
    private List<String> ticketPingRoles=null;
    private MessageEmbed ticketEmbed=null;
    private Button button=null;

    public TicketButton(String id) {
        this.id=id;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEmojiId() {
        return emojiId;
    }

    public void setEmojiId(String emojiId) {
        this.emojiId = emojiId;
    }

    public String getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(String ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public String getTicketNameFormat() {
        return ticketNameFormat;
    }

    public void setTicketNameFormat(String ticketNameFormat) {
        this.ticketNameFormat = ticketNameFormat;
    }

    public List<String> getTicketRolesToBeAdded() {
        return ticketRolesToBeAdded;
    }

    public void setTicketRolesToBeAdded(List<String> ticketRolesToBeAdded) {
        this.ticketRolesToBeAdded = ticketRolesToBeAdded;
    }

    public List<String> getTicketPingRoles() {
        return ticketPingRoles;
    }

    public void setTicketPingRoles(List<String> ticketPingRoles) {
        this.ticketPingRoles = ticketPingRoles;
    }

    public MessageEmbed getTicketEmbed() {
        return ticketEmbed;
    }

    public void setTicketEmbed(MessageEmbed ticketEmbed) {
        this.ticketEmbed = ticketEmbed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketButton)) return false;
        TicketButton that = (TicketButton) o;
        return id.equals(that.id) && Objects.equals(label, that.label) && Objects.equals(emojiId, that.emojiId) && Objects.equals(ticketCategoryId, that.ticketCategoryId) && Objects.equals(ticketNameFormat, that.ticketNameFormat) && Objects.equals(ticketRolesToBeAdded, that.ticketRolesToBeAdded) && Objects.equals(ticketPingRoles, that.ticketPingRoles) && Objects.equals(ticketEmbed, that.ticketEmbed) && Objects.equals(button, that.button);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, emojiId, ticketCategoryId, ticketNameFormat, ticketRolesToBeAdded, ticketPingRoles, ticketEmbed, button);
    }

}
