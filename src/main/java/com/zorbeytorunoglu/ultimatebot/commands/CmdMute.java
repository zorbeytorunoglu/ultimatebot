package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdMute implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdMute(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("mute",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        String[] args=event.getMessage().getContentRaw().split(" ");

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



    }

    @Override
    public String getCommand() {
        return null;
    }
}
