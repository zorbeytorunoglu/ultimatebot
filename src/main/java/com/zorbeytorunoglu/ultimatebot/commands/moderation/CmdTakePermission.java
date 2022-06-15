package com.zorbeytorunoglu.ultimatebot.commands.moderation;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.MCPLCommand;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import com.zorbeytorunoglu.ultimatebot.utils.StringUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CmdTakePermission implements MCPLCommand {

    private final Bot bot;
    private final Messages messages;

    public CmdTakePermission(Bot bot) {
        this.bot=bot;
        this.messages=bot.getMessageHandler().getMessages();
    }

    @Override
    public void execute(Bot bot, MessageReceivedEvent event, String[] args) {
        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        if (!bot.getPermissionHandler().hasPermission("takepermission",event.getMember())) {
            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getNoPermissionTitle(),messages.getNoPermission(),messages.getNoPermissionColor()
            )).queue();
            return;
        }

        if (args.length!=3) {
            event.getMessage().replyEmbeds(BotUtils.getEmbed(
                    messages.getTakePermissionUsageTitle(),messages.getTakePermissionUsage(),messages.getTakePermissionUsageColor()
            )).queue();
            return;
        }

        if (BotUtils.getRole(event.getMessage().getMentions())==null && BotUtils.getRole(event.getGuild(), args[1])==null) {
            Member member=null;
            if (StringUtils.isNumeric(args[1])) {
                if (BotUtils.getMember(event.getGuild(), args[1])!=null) member=BotUtils.getMember(event.getGuild(), args[1]);
            } else {
                if (BotUtils.getMember(event.getMessage().getMentions())!=null) member=BotUtils.getMember(event.getMessage().getMentions());
            }
            if (member==null) {
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getMemberNotFoundTitle(),messages.getMemberNotFound(),messages.getMemberNotFoundColor()
                )).queue();
                return;
            }

            if (!bot.getPermissionHandler().getUserPermissions().get(args[2]).contains(member.getId())) {
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getMemberAlreadyDontHavePermissionTitle(), messages.getMemberAlreadyDontHavePermission(),messages.getMemberAlreadyDontHavePermissionColor()
                )).queue();
                return;
            }

            BotUtils.takePermission(bot,args[2],member);

            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getPermissionTakenTitle(),messages.getPermissionTaken().replace("%entity%", member.getEffectiveName()),messages.getPermissionTakenColor()
            )).queue();
            return;

        } else {

            Role role = null;

            if (StringUtils.isNumeric(args[1])) {
                if (BotUtils.getRole(event.getGuild(), args[1])!=null) role=BotUtils.getRole(event.getGuild(), args[1]);
            } else {
                if (BotUtils.getRole(event.getMessage().getMentions())!=null) role=BotUtils.getRole(event.getMessage().getMentions());
            }
            if (role==null) {
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getRoleNotFoundTitle(),messages.getRoleNotFound(),messages.getRoleNotFoundColor()
                )).queue();
                return;
            }

            if (bot.getPermissionHandler().hasPermission(args[2], role)) {
                event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                        messages.getRoleAlreadyHasPermissionTitle(),messages.getRoleAlreadyHasPermission(),messages.getRoleAlreadyHasPermissionColor()
                )).queue();
                return;
            }

            BotUtils.takePermission(bot,args[2],role);

            event.getTextChannel().sendMessageEmbeds(BotUtils.getEmbed(
                    messages.getPermissionTakenTitle(),messages.getPermissionTaken().replace("%entity%", role.getName()),messages.getPermissionTakenColor()
            )).queue();
            return;

        }

    }

    @Override
    public String getCommand() {
        return bot.getCommandsHandler().getCommands().getPrefix()+bot.getCommandsHandler().getCommands().getTakePermission();
    }
}
