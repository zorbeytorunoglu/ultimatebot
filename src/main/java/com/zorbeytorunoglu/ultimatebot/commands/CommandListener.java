package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.commands.admin.CmdSetPrefix;
import com.zorbeytorunoglu.ultimatebot.commands.admin.CmdSetStatus;
import com.zorbeytorunoglu.ultimatebot.commands.misc.CmdAnnounce;
import com.zorbeytorunoglu.ultimatebot.commands.misc.CmdPing;
import com.zorbeytorunoglu.ultimatebot.commands.moderation.*;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdAdd;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdRemove;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdTicketPanel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private final Bot bot;
    private final CmdSetStatus cmdSetStatus;
    private final CmdSetPrefix cmdSetPrefix;
    private final CmdGivePermission cmdGivePermission;
    private final CmdKick cmdKick;
    private final CmdTakePermission cmdTakePermission;
    private final CmdBan cmdBan;
    private final CmdUnban cmdUnban;
    private final CmdPing cmdPing;
    private final CmdAnnounce cmdAnnounce;
    private final CmdMute cmdMute;
    private final CmdUnmute cmdUnmute;
    private final CmdPurge cmdPurge;
    private final CmdSlowmode cmdSlowmode;
    private final CmdWarn cmdWarn;
    private final CmdTicketPanel cmdTicketPanel;
    private final CmdAdd cmdAdd;
    private final CmdRemove cmdRemove;

    public CommandListener(Bot bot, CmdSetStatus cmdSetStatus,
                           CmdSetPrefix cmdSetPrefix,
                           CmdGivePermission cmdGivePermission,
                           CmdTakePermission cmdTakePermission,
                           CmdKick cmdKick, CmdBan cmdBan,
                           CmdUnban cmdUnban, CmdPing cmdPing,
                           CmdAnnounce cmdAnnounce, CmdMute cmdMute,
                           CmdUnmute cmdUnmute, CmdPurge cmdPurge,
                           CmdSlowmode cmdSlowmode, CmdWarn cmdWarn,
                           CmdTicketPanel cmdTicketPanel, CmdAdd cmdAdd,
                           CmdRemove cmdRemove) {
        this.bot=bot;
        this.cmdSetStatus=cmdSetStatus;
        this.cmdSetPrefix=cmdSetPrefix;
        this.cmdGivePermission=cmdGivePermission;
        this.cmdTakePermission=cmdTakePermission;
        this.cmdKick=cmdKick;
        this.cmdBan=cmdBan;
        this.cmdUnban=cmdUnban;
        this.cmdPing=cmdPing;
        this.cmdAnnounce=cmdAnnounce;
        this.cmdMute=cmdMute;
        this.cmdUnmute=cmdUnmute;
        this.cmdPurge=cmdPurge;
        this.cmdSlowmode=cmdSlowmode;
        this.cmdWarn=cmdWarn;
        this.cmdTicketPanel=cmdTicketPanel;
        this.cmdAdd=cmdAdd;
        this.cmdRemove=cmdRemove;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] args=event.getMessage().getContentRaw().split(" ");

        if (args[0].equals(cmdSetStatus.getCommand())) {
            cmdSetStatus.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdSetPrefix.getCommand())) {
            cmdSetPrefix.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdGivePermission.getCommand())) {
            cmdGivePermission.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdTakePermission.getCommand())) {
            cmdTakePermission.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdKick.getCommand())) {
            cmdKick.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdBan.getCommand())) {
            cmdBan.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdUnban.getCommand())) {
            cmdUnban.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdPing.getCommand())) {
            cmdPing.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdAnnounce.getCommand())) {
            cmdAnnounce.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdMute.getCommand())) {
            cmdMute.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdUnmute.getCommand())) {
            cmdUnmute.execute(bot,event, args); return;
        }

        if (args[0].equals(cmdPurge.getCommand())) {
            cmdPurge.execute(bot,event,args); return;
        }

        if (args[0].equals(cmdSlowmode.getCommand())) {
            cmdSlowmode.execute(bot,event,args); return;
        }

        if (args[0].equals(cmdWarn.getCommand())) {
            cmdWarn.execute(bot,event,args); return;
        }

        if (args[0].equals(cmdTicketPanel.getCommand())) {
            cmdTicketPanel.execute(bot,event,args); return;
        }

        if (args[0].equals(cmdAdd.getCommand())) {
            cmdAdd.execute(bot,event,args); return;
        }

        if (args[0].equals(cmdRemove.getCommand())) {
            cmdRemove.execute(bot,event,args); return;
        }

    }
}
