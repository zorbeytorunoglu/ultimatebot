package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.datas.Mute;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.services.Executor;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CmdMute implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdMute(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;
        if (!bot.getPermissionHandler().hasPermission("mute",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length==1) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMuteUsageTitle(),messages.getMuteUsage().replace("%command%",getCommand()),messages.getMuteUsageColor()
            )).queue();
            return;
        }
        Member member=BotUtils.getMember(event.getGuild(), event.getMessage().getMentions(), args[1]);

        if (member==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMemberNotFoundTitle(),messages.getMemberNotFound(),messages.getMemberNotFoundColor()
            )).queue();
            return;
        }

        if (BotUtils.isMuted(bot,member)) {
            Mute mute=BotUtils.getMute(member);
            if (mute!=null) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getAlreadyMutedTitle(),messages.getAlreadyMuted()
                                        .replace("%time%",mute.getMuteExpiration()==null ? messages.getForever() : StringUtils.formatDate(mute.getMuteExpiration()))
                                .replace("%reason%", mute.getReason()==null ? messages.getMuteNoReason() : mute.getReason())
                                .replace("%member%",member.getEffectiveName()),messages.getAlreadyMutedColor()
                )).queue();
            } else {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getAlreadyMutedTitle(),messages.getAlreadyMuted()
                                .replace("%time%",messages.getForever())
                                .replace("%reason%", messages.getMuteNoReason())
                                .replace("%member%",member.getEffectiveName()),messages.getAlreadyMutedColor()
                )).queue();
            }
            return;
        }

        if (args.length==2) {

            if (BotUtils.getMute(event.getMember())!=null) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getAlreadyMutedTitle(), messages.getAlreadyMuted().replace("%member%",member.getEffectiveName()),messages.getAlreadyMutedColor()
                )).queue();
                return;
            }

            Role mutedRole=BotUtils.getMutedRole(bot, event.getGuild());

            if (!member.getGuild().getSelfMember().canInteract(mutedRole)) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherColor()
                )).queue();
                return;
            }

            Mute mute=new Mute(member.getIdLong());

            Mute.getMutes().add(mute);

            BotUtils.mute(bot,member);

            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getJustMutedTitle(),messages.getJustMuted().replace("%member%",member.getEffectiveName()),messages.getJustMutedColor()
            )).queue();

            return;

        }

        if (args.length==3) {

            if (BotUtils.getMute(event.getMember())!=null) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getAlreadyMutedTitle(), messages.getAlreadyMuted().replace("%member%",member.getEffectiveName()),messages.getAlreadyMutedColor()
                )).queue();
                return;
            }

            Role mutedRole=BotUtils.getMutedRole(bot, event.getGuild());

            if (!member.getGuild().getSelfMember().canInteract(mutedRole)) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherColor()
                )).queue();
                return;
            }

            int minutes;

            try {
                minutes=Integer.parseInt(args[2]);
            } catch (NumberFormatException exception) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getInvalidNumberTitle(),messages.getInvalidNumber(),messages.getInvalidNumberColor()
                )).queue();
                return;
            }

            if (minutes<=0) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getGreaterThanZeroTitle(),messages.getGreaterThanZero(),messages.getGreaterThanZeroColor()
                )).queue();
                return;
            }

            if (minutes>bot.getSettingsHandler().getSettings().getMaxMuteMinute()) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getBiggerThanMaxMuteTitle(),messages.getBiggerThanMaxMute().replace("%maxminute%", bot.getSettingsHandler().getSettings().getMaxMuteMinute()+""),
                        messages.getBiggerThanMaxMuteColor()
                )).queue();
                return;
            }

            Mute mute=new Mute(member.getIdLong());

            Date expiresIn = new Date(Calendar.getInstance().getTimeInMillis() + (10 * 60 * 1000));

            mute.setMuteExpiration(expiresIn);

            Mute.getMutes().add(mute);

            BotUtils.mute(bot,member,minutes);

            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMutedUntilTitle(),messages.getMutedUntil().replace("%member%",member.getEffectiveName())
                            .replace("%time%", StringUtils.formatDuration(bot.getMessageHandler().getMessages(), TimeUnit.MINUTES.toSeconds(minutes))), messages.getMutedUntilColor()
            )).queue();

            return;

        }

        if (args.length>3) {
            if (BotUtils.getMute(event.getMember())!=null) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getAlreadyMutedTitle(), messages.getAlreadyMuted().replace("%member%",member.getEffectiveName()),messages.getAlreadyMutedColor()
                )).queue();
                return;
            }

            Role mutedRole=BotUtils.getMutedRole(bot, event.getGuild());

            if (!member.getGuild().getSelfMember().canInteract(mutedRole)) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherTitle(),messages.getMuteRoleIsPositionedHigherColor()
                )).queue();
                return;
            }

            int minutes;

            try {
                minutes=Integer.parseInt(args[2]);
            } catch (NumberFormatException exception) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getInvalidNumberTitle(),messages.getInvalidNumber(),messages.getInvalidNumberColor()
                )).queue();
                return;
            }

            if (minutes<=0) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getGreaterThanZeroTitle(),messages.getGreaterThanZero(),messages.getGreaterThanZeroColor()
                )).queue();
                return;
            }

            StringBuilder stringBuilder=new StringBuilder();

            for (int i=3; i<args.length; i++) {
                if (i==args.length-1) {
                    stringBuilder.append(args[i]);
                } else {
                    stringBuilder.append(args[i]).append(" ");
                }
            }

            Mute mute=new Mute(member.getIdLong());

            Date expiresIn = new Date(Calendar.getInstance().getTimeInMillis() + (10 * 60 * 1000));

            mute.setMuteExpiration(expiresIn);

            mute.setReason(stringBuilder.toString());

            Mute.getMutes().add(mute);

            BotUtils.mute(bot,member,minutes);

            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMuteReasonTitle(),messages.getMuteReason().replace("%reason%",stringBuilder.toString())
                            .replace("%member%",member.getEffectiveName())
                            .replace("%time%", StringUtils.formatDuration(bot.getMessageHandler().getMessages(), TimeUnit.MINUTES.toSeconds(minutes))), messages.getMuteReasonColor()
            )).queue();

            return;
        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getMute();
    }
}
