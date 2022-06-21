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

public class CmdAdd implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdAdd(Bot bot) {
        this.bot = bot;
        this.messages = bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("add",event.getMember())) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor())).queue();
            return;
        }

        if (args.length!=2) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getAddUsageTitle(),
                    messages.getAddUsage().replace("%command%",getCommand()),
                    messages.getAddUsageColor()
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
                bot.getSettingsHandler().getSettings().getTicketMemberAddPermissions(), Collections.EMPTY_LIST).queue();

        event.getMessage().replyEmbeds(BotUtils.getEmbed(
                messages.getMemberAddedToTicketTitle(),
                messages.getMemberAddedToTicket().replace("%member%",member.getAsMention()),
                messages.getMemberAddedToTicketColor()
        )).queueAfter(25L, TimeUnit.MILLISECONDS);

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getAdd();
    }
}
