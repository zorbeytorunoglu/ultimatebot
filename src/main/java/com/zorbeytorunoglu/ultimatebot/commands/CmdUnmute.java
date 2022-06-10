package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdUnmute implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdUnmute(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("unmute",event.getMember())) {
            event.getMessage().replyEmbeds(
                    BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())
            ).queue();
            return;
        }

        if (args.length==1) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getUnmuteUsageTitle(),messages.getUnmuteUsage().replace("%command%",getCommand()),messages.getUnmuteUsageColor()
            )).queue();
            return;
        }

        Member member=BotUtils.getMember(event.getGuild(),event.getMessage().getMentions(),args[1]);

        if (member==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(messages.getMemberNotFoundTitle(),
                    messages.getMemberNotFound(),messages.getMemberNotFoundColor())).queue();
            return;
        }

        if (!BotUtils.isMuted(bot,member)) {
            if (BotUtils.getMute(member)!=null) BotUtils.removeMute(BotUtils.getMute(member));
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getNotMutedTitle(), messages.getNotMuted().replace("%member%", member.getAsMention()), messages.getNotMutedColor()
            )).queue();
            return;
        }

        BotUtils.unMute(bot, event.getGuild(), member.getId());

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getNoLongerMutedTitle(),
                messages.getNoLongerMuted().replace("%member%",member.getAsMention()),
                messages.getNoLongerMutedColor()
        )).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getUnmute();
    }
}
