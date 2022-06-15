package com.zorbeytorunoglu.ultimatebot.configuration.tickets;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketPanel {

    private final String panelId;
    private String title=null;
    private String author=null;
    private Color color=null;
    private String footer=null;
    private String footerImgUrl=null;
    private String thumbnailImgUrl=null;
    private String authorUrl=null;
    private String authorImgUrl=null;
    private String description=null;
    private MessageEmbed ticketEmbed=null;
    private List<MessageEmbed.Field> fields=new ArrayList<>();
    private List<TicketButton> ticketButtons=new ArrayList<>();

    public TicketPanel(String panelId) {
        this.panelId=panelId;
    }

    public MessageEmbed getTicketEmbed() {
        return ticketEmbed;
    }

    public void setTicketEmbed(MessageEmbed ticketEmbed) {
        this.ticketEmbed = ticketEmbed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TicketButton> getTicketButtons() {
        return ticketButtons;
    }

    public void setTicketButtons(List<TicketButton> ticketButtons) {
        this.ticketButtons = ticketButtons;
    }

    public String getPanelId() {
        return panelId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getFooterImgUrl() {
        return footerImgUrl;
    }

    public void setFooterImgUrl(String footerImgUrl) {
        this.footerImgUrl = footerImgUrl;
    }

    public String getThumbnailImgUrl() {
        return thumbnailImgUrl;
    }

    public void setThumbnailImgUrl(String thumbnailImgUrl) {
        this.thumbnailImgUrl = thumbnailImgUrl;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MessageEmbed.Field> getFields() {
        return fields;
    }

    public void setFields(List<MessageEmbed.Field> fields) {
        this.fields = fields;
    }

}
