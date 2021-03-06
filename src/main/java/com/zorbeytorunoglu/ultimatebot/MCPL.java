package com.zorbeytorunoglu.ultimatebot;

import com.zorbeytorunoglu.ultimatebot.commands.*;
import com.zorbeytorunoglu.ultimatebot.commands.admin.CmdSetPrefix;
import com.zorbeytorunoglu.ultimatebot.commands.admin.CmdSetStatus;
import com.zorbeytorunoglu.ultimatebot.commands.misc.CmdAnnounce;
import com.zorbeytorunoglu.ultimatebot.commands.misc.CmdPing;
import com.zorbeytorunoglu.ultimatebot.commands.moderation.*;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdAdd;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdRemove;
import com.zorbeytorunoglu.ultimatebot.commands.ticket.CmdTicketPanel;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.commands.CommandsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.data.DataHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.MessageHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.settings.SettingsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketHandler;
import com.zorbeytorunoglu.ultimatebot.gui.GUI;
import com.zorbeytorunoglu.ultimatebot.listeners.Ticket;
import com.zorbeytorunoglu.ultimatebot.listeners.Welcome;
import com.zorbeytorunoglu.ultimatebot.permissions.PermissionHandler;
import com.zorbeytorunoglu.ultimatebot.services.Executor;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class MCPL {

    public static void main(String[] args) throws LoginException {

        Resource settingsResource=new Resource("settings.yml");
        Resource commandsResource=new Resource("commands.json");
        Resource permissionsResource=new Resource("permissions.yml");
        Resource messagesResource=new Resource("messages.json");
        Resource dataResource=new Resource("data.yml");
        Resource ticketPanelsResource=new Resource("ticket-panels.yml");

        SettingsHandler settingsHandler=new SettingsHandler(settingsResource);
        PermissionHandler permissionHandler=new PermissionHandler(permissionsResource);
        MessageHandler messageHandler=new MessageHandler(messagesResource);
        CommandsHandler commandsHandler=new CommandsHandler(commandsResource);
        DataHandler dataHandler=new DataHandler(dataResource);
        TicketHandler ticketHandler=new TicketHandler(ticketPanelsResource);

        Bot bot=new Bot(settingsHandler,permissionHandler, messageHandler, commandsHandler,dataHandler,ticketHandler);

        GUI gui = new GUI(bot);
        gui.init();

        Executor.init();

        JDABuilder builder = JDABuilder.createDefault(settingsHandler.getSettings().getToken(), GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_MESSAGES);

        builder.setActivity(BotUtils.getActivity(bot));

        builder.addEventListeners(new Ticket(bot),
                new Welcome(bot),

                new CommandListener(bot,
                new CmdSetStatus(bot),
                new CmdSetPrefix(bot),
                new CmdGivePermission(bot),
                new CmdTakePermission(bot),
                new CmdKick(bot),
                new CmdBan(bot),
                new CmdUnban(bot),
                new CmdPing(bot),
                new CmdAnnounce(bot),
                new CmdMute(bot),
                new CmdUnmute(bot),
                new CmdPurge(bot),
                new CmdSlowmode(bot),
                new CmdWarn(bot),
                new CmdTicketPanel(bot),
                        new CmdAdd(bot),
                        new CmdRemove(bot)));

        JDA jda=builder.build();

        bot.setJda(jda);

        dataHandler.load(bot);

    }

}
