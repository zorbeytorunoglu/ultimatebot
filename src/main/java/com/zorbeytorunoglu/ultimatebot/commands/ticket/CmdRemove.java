package com.zorbeytorunoglu.ultimatebot.commands.ticket;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.TicketUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CmdRemove implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdRemove(Bot bot) {
        this.bot = bot;
        this.messages = bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("remove",event.getMember())) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())).queue();
            return;
        }

        if (args.length!=2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getRemoveUsageTitle(),
                    messages.getRemoveUsage().replace("%command%",getCommand()),
                    messages.getRemoveUsageColor()
            )).queue();
            return;
        }

        if (!TicketUtils.isTicket(event.getTextChannel())) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getNotTicketTitle(),
                    messages.getNotTicket(),
                    messages.getNotTicketColor()
            )).queue();
            return;
        }

        Member member=BotUtils.getMember(event.getGuild(),event.getMessage().getMentions(),args[1]);

        if (member==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getMemberNotFoundTitle(),
                    messages.getMemberNotFound(),
                    messages.getMemberNotFoundColor()
            )).queue();
            return;
        }

        event.getTextChannel().getManager().putMemberPermissionOverride(member.getIdLong(),
                Collections.EMPTY_LIST, bot.getSettingsHandler().getSettings().getTicketMemberAddPermissions()).queue();

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getMemberRemovedFromTicketTitle(),
                messages.getMemberRemovedFromTicket().replace("%member%",member.getAsMention()),
                messages.getMemberRemovedFromTicketColor()
        )).queueAfter(25L, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getRemove();
    }
}
