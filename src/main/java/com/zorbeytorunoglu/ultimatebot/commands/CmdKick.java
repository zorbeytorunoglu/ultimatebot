package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdKick implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdKick(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("kick",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        String[] args=event.getMessage().getContentRaw().split(" ");

        if (args.length<2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getKickUsageTitle(),messages.getKickUsage().replace("%command%",getCommand()),messages.getKickUsageColor()
            )).queue();
            return;
        }

        Member member=BotUtils.getMember(event.getMessage().getMentions())==null ? BotUtils.getMember(event.getGuild(), args[1]) : BotUtils.getMember(event.getMessage().getMentions());

        if (member==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMemberNotFoundTitle(),messages.getMemberNotFound(),messages.getMemberNotFoundColor()
            )).queue();
            return;
        }

        if (args.length==2) {
            member.kick().queue();
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getKickNoReasonTitle(),messages.getKickNoReason(),messages.getKickNoReasonColor()
            )).queue();
            return;
        }

        StringBuilder stringBuilder=new StringBuilder();

        for (int i=2; i<args.length; i++) {
            if (i==args.length-1) {
                stringBuilder.append(args[i]);
            } else {
                stringBuilder.append(args[i]).append(" ");
            }
        }

        member.kick(stringBuilder.toString()).queue();

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getKickReasonTitle(),messages.getKickReason().replace("%reason%", stringBuilder.toString())
                        .replace("%member%",member.getEffectiveName()),messages.getKickReasonColor()
        )).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getKick();
    }
}
