package com.zorbeytorunoglu.ultimatebot.commands;

import com.zorbeytorunoglu.ultimatebot.Bot;
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

    public CommandListener(Bot bot, CmdSetStatus cmdSetStatus,
                           CmdSetPrefix cmdSetPrefix,
                           CmdGivePermission cmdGivePermission,
                           CmdTakePermission cmdTakePermission,
                           CmdKick cmdKick, CmdBan cmdBan,
                           CmdUnban cmdUnban, CmdPing cmdPing) {
        this.bot=bot;
        this.cmdSetStatus=cmdSetStatus;
        this.cmdSetPrefix=cmdSetPrefix;
        this.cmdGivePermission=cmdGivePermission;
        this.cmdTakePermission=cmdTakePermission;
        this.cmdKick=cmdKick;
        this.cmdBan=cmdBan;
        this.cmdUnban=cmdUnban;
        this.cmdPing=cmdPing;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;
        if (event.getAuthor().isBot()) return;

        String[] args=event.getMessage().getContentRaw().split(" ");

        if (args[0].equals(cmdSetStatus.getCommand())) {
            cmdSetStatus.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdSetPrefix.getCommand())) {
            cmdSetPrefix.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdGivePermission.getCommand())) {
            cmdGivePermission.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdTakePermission.getCommand())) {
            cmdTakePermission.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdKick.getCommand())) {
            cmdKick.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdBan.getCommand())) {
            cmdBan.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdUnban.getCommand())) {
            cmdUnban.execute(bot,event);
            return;
        }

        if (args[0].equals(cmdPing.getCommand())) {
            cmdPing.execute(bot,event);
            return;
        }

    }

}
