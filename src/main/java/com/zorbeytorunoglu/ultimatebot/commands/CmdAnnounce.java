package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdAnnounce implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdAnnounce(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("announce", event.getMember())) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }

        String[] args=event.getMessage().getContentRaw().split(" ");

        if (args.length<3) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getAnnounceUsageTitle(),messages.getAnnounceUsage().replace("%command%",getCommand()),messages.getAnnounceUsageColor()
            )).queue();
            return;
        }

        GuildChannel guildChannel=BotUtils.getGuildChannel(event.getGuild(), event.getMessage().getMentions(), args[1]);

        if (guildChannel==null) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getChannelNotFoundTitle(),messages.getChannelNotFound(),messages.getChannelNotFoundColor()
            )).queue();
            return;
        }

        if (guildChannel.getType()!= ChannelType.TEXT) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getNotTextChannelTitle(),messages.getNotTextChannel(),messages.getNotTextChannelColor()
            )).queue();
            return;
        }

        StringBuilder stringBuilder=new StringBuilder();
        for (int i=2; i<args.length; i++) {
            if (i!= args.length-1) {
                stringBuilder.append(args[i]).append(" ");
            } else {
                stringBuilder.append(args[i]);
            }
        }

        String[] announcementArgs=stringBuilder.toString().split("%%");

        TextChannel textChannel=(TextChannel)guildChannel;

        textChannel.sendMessageEmbeds(BotUtils.getEmbed(
                announcementArgs[0], announcementArgs[1], announcementArgs[2]
                )).queue();

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getAnnounce();
    }
}
