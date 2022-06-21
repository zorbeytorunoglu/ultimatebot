package com.zorbeytorunoglu.ultimatebot.listeners;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Welcome extends ListenerAdapter {

    private final Bot bot;
    private final Messages messages;

    public Welcome(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        if (!bot.getSettingsHandler().getSettings().getWelcomeEnabled()) return;

        if (bot.getSettingsHandler().getSettings().getWelcomeTextChannelId()==null) return;

        TextChannel textChannel=event.getGuild().getTextChannelById(bot.getSettingsHandler().getSettings().getWelcomeTextChannelId());

        if (textChannel==null) return;

        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setAuthor(bot.getSettingsHandler().getSettings().getWelcomeEmbedAuthor().replace("%member_tag%",event.getMember().getEffectiveName())
                        .replace("%member_name%",event.getMember().getEffectiveName()),
                bot.getSettingsHandler().getSettings().getWelcomeEmbedAuthorUrl(),bot.getSettingsHandler().getSettings().getWelcomeEmbedAuthorImgUrl()
                        .replace("%member_avatar_url%",event.getMember().getEffectiveAvatarUrl()));
        embedBuilder.setFooter(bot.getSettingsHandler().getSettings().getWelcomeEmbedFooter().replace("%member_name%",event.getMember().getEffectiveName())
                        .replace("%member_tag%",event.getMember().getAsMention())
                ,bot.getSettingsHandler().getSettings().getWelcomeEmbedFooterImgUrl().replace("%member_avatar_url%", event.getMember().getEffectiveAvatarUrl()));
        embedBuilder.setColor(bot.getSettingsHandler().getSettings().getWelcomeEmbedColor());
        embedBuilder.setDescription(bot.getSettingsHandler().getSettings().getWelcomeEmbedDescription().replace("%member_tag%",event.getMember().getAsMention())
                .replace("%member_name%", event.getMember().getEffectiveName()));
        embedBuilder.setTitle(bot.getSettingsHandler().getSettings().getWelcomeEmbedTitle()
                .replace("%member_name%",event.getMember().getEffectiveName()).replace("%member_tag%",event.getMember().getAsMention()));
        embedBuilder.setThumbnail(bot.getSettingsHandler().getSettings().getWelcomeEmbedThumbnail().replace("%member_avatar_url%",
                event.getMember().getEffectiveAvatarUrl()));
        if (!bot.getSettingsHandler().getSettings().getWelcomeEmbedFields().isEmpty()) {
            for (MessageEmbed.Field field:bot.getSettingsHandler().getSettings().getWelcomeEmbedFields()) {
                embedBuilder.addField(field);
            }
        }

        textChannel.sendMessageEmbeds(embedBuilder.build()).queueAfter(20L, TimeUnit.MILLISECONDS);

    }
}
