package com.zorbeytorunoglu.ultimatebot.commands.moderation;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.data.Warn;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class CmdWarn implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdWarn(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("warn", Objects.requireNonNull(event.getMember()))) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }

        if (args.length<2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getWarnUsageTitle(),
                    messages.getWarnUsage().replace("%command%",getCommand()),
                    messages.getWarnUsageColor()
            )).queue();
            return;
        }

        Member member=BotUtils.getMember(event.getGuild(), event.getMessage().getMentions(), args[1]);

        if (member==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMemberNotFoundTitle(),
                    messages.getMemberNotFound(),
                    messages.getMemberNotFoundColor()
            )).queue();
            return;
        }

        int warns=0;

        if (Warn.getWarns().containsKey(member.getId())) warns=Warn.getWarns().get(member.getId());

        warns+=1;

        if (warns>=bot.getSettingsHandler().getSettings().getWarnLimit()) {

            event.getMessage().delete().queue();

            Warn.getWarns().remove(member.getId());

            switch (bot.getSettingsHandler().getSettings().getWarnAction()) {
                case "ban":
                    event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                            messages.getWarnBannedTitle(),
                            messages.getWarnBanned().replace("%member%",member.getAsMention()),
                            messages.getWarnBannedColor()
                    )).queue();
                    member.ban(0,messages.getWarnBanReason().replace("%member%",member.getEffectiveName())).queue();
                    return;
                default:
                    event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                            messages.getWarnKickedTitle(),
                            messages.getWarnKicked().replace("%member%",member.getAsMention()),
                            messages.getWarnKickedColor()
                    )).queue();
                    member.kick().queue();
                    return;
            }

        } else {

            Warn.getWarns().put(member.getId(),warns);

            if (args.length>2) {
                StringBuilder stringBuilder=new StringBuilder();
                for (int i=2; i<args.length; i++) {
                    if (i==args.length-1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i]).append(" ");
                    }
                }

                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getWarnedReasonTitle(),
                        messages.getWarnedReason().replace("%reason%",stringBuilder.toString()).replace("%warns%", warns+"").replace("%staff%",event.getMember().getAsMention()).replace("%member%",member.getAsMention()),
                        messages.getWarnedReasonColor()
                )).queue();

            } else {
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getWarnedTitle(),
                        messages.getWarned().replace("%staff%",event.getMember().getAsMention()).replace("%warns%", warns+"").replace("%member%",member.getAsMention()),
                        messages.getWarnedColor()
                )).queue();

            }
            return;

        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getWarn();
    }
}
