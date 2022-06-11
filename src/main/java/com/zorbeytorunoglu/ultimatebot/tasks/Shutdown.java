package com.zorbeytorunoglu.ultimatebot.tasks;

import com.zorbeytorunoglu.ultimatebot.Bot;

public class Shutdown {

    public static void init(Bot bot) {

        bot.getDataHandler().save(bot);

    }

}
