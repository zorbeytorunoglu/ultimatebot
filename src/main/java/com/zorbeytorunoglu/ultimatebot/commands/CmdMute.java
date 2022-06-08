package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.datas.Mute;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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

            event.getGuild().addRoleToMember(member,mutedRole).queue();

            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getJustMutedTitle(),messages.getJustMuted().replace("%member%",member.getEffectiveName()),messages.getJustMutedColor()
            )).queue();

            return;

        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getMute();
    }
}
