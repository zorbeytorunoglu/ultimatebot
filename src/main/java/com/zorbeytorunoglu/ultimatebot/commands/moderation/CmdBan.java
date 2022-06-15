package com.zorbeytorunoglu.ultimatebot.commands.moderation;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdBan implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdBan(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;

        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("ban", event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length==1) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getBanUsageTitle(),messages.getBanUsage().replace("%command%", getCommand()),messages.getBanUsageColor()
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

        if (args.length==2) {
            member.ban(0).queue();
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getBannedTitle(),messages.getBanned().replace("%member%",member.getEffectiveName()),messages.getBannedColor()
            )).queue();
            return;
        }

        if (args.length==3) {
            int days;
            try {
                days=Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                event.getMessage().replyEmbeds(BotUtils.getEmbed(
                        messages.getInvalidNumberTitle(),messages.getInvalidNumber(),messages.getInvalidNumberColor()
                )).queue();
                return;
            }
            member.ban(days).queue();
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getBannedTitle(),messages.getBanned().replace("%member%",member.getEffectiveName()),messages.getBannedColor()
            )).queue();
            return;
        }

        int days;
        try {
            days=Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getInvalidNumberTitle(),messages.getInvalidNumber(),messages.getInvalidNumberColor()
            )).queue();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=3; i<args.length; i++) {
            if (i==args.length-1) {
                stringBuilder.append(args[i]);
            } else {
                stringBuilder.append(args[i]).append(" ");
            }
        }

        member.ban(days,stringBuilder.toString()).queue();

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getBannedReasonTitle(),messages.getBannedReason().replace("%reason%",stringBuilder.toString())
                        .replace("%member%", member.getEffectiveName()),messages.getBannedReasonColor()
        )).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getBan();
    }
}
