package com.zorbeytorunoglu.ultimatebot.configuration.settings;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class SettingsContainer {

    public final String token;
    public final String activity;
    public final String activityLabel;
    public final String mutedRoleName;
    public final Integer maxMuteMinute;
    public final Integer maxPurgeNumber;
    public final Integer maxSlowmode;
    public final Integer defaultSlowmode;
    public final Integer warnLimit;
    public final String warnAction;
    public final Integer memberTicketLimit;
    public final String ticketAddMemberPerms;
    public final boolean welcomeEnabled;
    public final String welcomeEmbedTitle;
    public final String welcomeEmbedDescription;
    public final Color welcomeEmbedColor;
    public final String welcomeEmbedFooter;
    public final String welcomeEmbedFooterImgUrl;
    public final String welcomeEmbedAuthor;
    public final String welcomeEmbedAuthorUrl;
    public final String welcomeEmbedAuthorImgUrl;
    public final String welcomeEmbedThumbnailUrl;
    public final Collection<MessageEmbed.Field> welcomeEmbedFields;
    public final String welcomeTextChannel;

    public SettingsContainer(Configuration configuration) {

        this.token=configuration.getString("token");
        this.activity=configuration.getString("activity");
        this.activityLabel=configuration.getString("activity-label");
        this.mutedRoleName=configuration.getString("mute.role-name");
        this.maxMuteMinute=configuration.getInt("mute.max-mute-min");
        this.maxPurgeNumber=configuration.getInt("purge.max-number");
        this.maxSlowmode=configuration.getInt("slowmode.max-minutes");
        this.defaultSlowmode=configuration.getInt("slowmode.default");
        this.warnLimit=configuration.getInt("warns.limit");
        this.warnAction=configuration.getString("warns.action");
        this.memberTicketLimit=configuration.getInt("tickets.member-ticket-limit");
        this.ticketAddMemberPerms=configuration.getString("tickets.member-add-perms");
        this.welcomeEnabled=configuration.getBoolean("welcome.enabled");
        this.welcomeEmbedTitle=configuration.getString("welcome.embed.title");
        this.welcomeEmbedDescription=configuration.getString("welcome.embed.description");
        this.welcomeEmbedFooter=configuration.getString("welcome.embed.footer");
        this.welcomeEmbedColor=configuration.getString("welcome.embed.color").equals("none") ? Color.BLACK : BotUtils.getColor(configuration.getString("welcome.embed.color"));
        this.welcomeEmbedFooterImgUrl=configuration.getString("welcome.embed.footer-img-url");
        this.welcomeEmbedAuthor=configuration.getString("welcome.embed.author");
        this.welcomeEmbedAuthorUrl=configuration.getString("welcome.embed.author-url");
        this.welcomeEmbedAuthorImgUrl=configuration.getString("welcome.embed.author-img-url");
        this.welcomeEmbedThumbnailUrl=configuration.getString("welcome.embed.thumbnail-img-url");
        this.welcomeEmbedFields=new ArrayList<>();
        if (configuration.getSection("welcome.embed.fields")!=null) {
            Collection<String> keyCollection=configuration.getSection("welcome.embed.fields").getKeys();
            for (String key:keyCollection) {
                welcomeEmbedFields.add(new MessageEmbed.Field(configuration.getString("welcome.embed.fields."+key+".title"),
                        configuration.getString("welcome.embed.fields."+key+".description"),
                        configuration.getBoolean("welcome.embed.fields."+key+".inline")));
            }
        }
        this.welcomeTextChannel=configuration.getString("welcome.channel")=="" ? null : configuration.getString("welcome.channel");

    }
}
