package com.zorbeytorunoglu.ultimatebot.configuration.tickets;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.*;

public class TicketButton {

    private final String id;
    private String label=null;
    private String emojiId=null;
    private String ticketCategoryId=null;
    private String ticketNameFormat=null;
    private List<String> ticketRolesToBeAdded=null;
    private HashMap<String, Collection<Permission>> allowedPermissions;
    private HashMap<String, Collection<Permission>> deniedPermissions;
    private List<String> ticketPingRoles=null;
    private EmbedBuilder embedBuilder=null;
    private String embedDescription=null;
    private Button button=null;
    private Collection<Permission> memberPermissions;

    public TicketButton(String id) {
        this.id=id;
    }

    public HashMap<String, Collection<Permission>> getAllowedPermissions() {
        return allowedPermissions;
    }

    public HashMap<String, Collection<Permission>> getDeniedPermissions() {
        return deniedPermissions;
    }

    public void setAllowedPermissions(HashMap<String, Collection<Permission>> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }

    public void setDeniedPermissions(HashMap<String, Collection<Permission>> deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
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

    public EmbedBuilder getEmbedBuilder() {
        return embedBuilder;
    }

    public void setEmbedBuilder(EmbedBuilder embedBuilder) {
        this.embedBuilder = embedBuilder;
    }

    public String getEmbedDescription() {
        return embedDescription;
    }

    public void setEmbedDescription(String embedDescription) {
        this.embedDescription = embedDescription;
    }

    public Collection<Permission> getMemberPermissions() {
        return memberPermissions;
    }

    public void setMemberPermissions(Collection<Permission> memberPermissions) {
        this.memberPermissions = memberPermissions;
    }

}
