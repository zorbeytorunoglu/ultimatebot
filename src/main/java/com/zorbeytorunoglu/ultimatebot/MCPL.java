package com.zorbeytorunoglu.ultimatebot;

import com.zorbeytorunoglu.ultimatebot.commands.*;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.commands.CommandsHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.datas.DataHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.messages.MessageHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.settings.SettingsHandler;
import com.zorbeytorunoglu.ultimatebot.gui.GUI;
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

        SettingsHandler settingsHandler=new SettingsHandler(settingsResource);
        PermissionHandler permissionHandler=new PermissionHandler(permissionsResource);
        MessageHandler messageHandler=new MessageHandler(messagesResource);
        CommandsHandler commandsHandler=new CommandsHandler(commandsResource);
        DataHandler dataHandler=new DataHandler(dataResource);

        Bot bot=new Bot(settingsHandler,permissionHandler, messageHandler, commandsHandler,dataHandler);

        GUI gui = new GUI(bot);
        gui.init();

        Executor.init();

        JDABuilder builder = JDABuilder.createDefault(settingsHandler.getSettings().getToken(), GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_MESSAGES);

        builder.setActivity(BotUtils.getActivity(bot));

        builder.addEventListeners(new CommandListener(bot,
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
                new CmdSlowmode(bot)));

        JDA jda=builder.build();

        bot.setJda(jda);

        dataHandler.load(bot);

    }

}
