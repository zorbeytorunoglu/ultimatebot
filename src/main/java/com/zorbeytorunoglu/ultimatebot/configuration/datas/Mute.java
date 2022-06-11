package com.zorbeytorunoglu.ultimatebot.configuration.datas;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;
import com.zorbeytorunoglu.ultimatebot.services.Executor;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Mute {
    private final Long memberId;
    private Date muteExpiration=null;
    private String reason=null;
    static final ArrayList<Mute> mutes=new ArrayList<>();
    private static Bot bot=null;

    public Mute(long memberId) {
        this.memberId=memberId;
    }
    public long getMemberId() {
        return memberId;
    }
    public Date getMuteExpiration() {
        return muteExpiration;
    }
    public void setMuteExpiration(Date muteExpiration) {
        this.muteExpiration = muteExpiration;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public static ArrayList<Mute> getMutes() {
        return mutes;
    }

    public static void setBot(Bot bot) {
        Mute.bot = bot;
    }

    private static Bot getBot() {
        return bot;
    }

    public static void load(Resource dataResource, Configuration configuration) {
        if (!dataResource.getFile().exists()) return;
        for (String id:configuration.getSection("mutes").getKeys()) {
            Mute mute=new Mute(Long.parseLong(id));
            mute.setReason(configuration.getString("mutes."+id+".reason")=="" ? null : configuration.getString("mutes."+id+".reason"));
            if (configuration.getString("mutes."+id+".expiresIn")!="") {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date expireDate = null;
                try {
                    expireDate=dateFormat.parse(configuration.getString(
                                    "mutes."+id+".expiresIn"
                            ));
                    mute.setMuteExpiration(expireDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date currentDate=new Date();

                long difference  = expireDate.getTime() - currentDate.getTime();

                if (difference>0) {
                    if (!Mute.getMutes().contains(mute)) Mute.getMutes().add(mute);
                    Executor.scheduleDelayedTask(() -> BotUtils.unMute(bot,bot.getJda().getGuilds().get(0),id), difference, TimeUnit.MILLISECONDS);
                } else {
                    if (Mute.getMutes().contains(mute)) Mute.getMutes().remove(mute);
                }

            }

            configuration.set("mutes."+id, null);

        }


        try {
            bot.getDataHandler().getYamlConfiguration().save(configuration,dataResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void save(YamlConfiguration yamlConfiguration, Configuration configuration, Resource dataResource) throws IOException {
        if (Mute.getMutes().isEmpty()) return;
        for (Mute mute:Mute.getMutes()) {
            if (mute.getReason()!=null)  configuration.set("mutes."+mute.getMemberId()+".reason", mute.getReason());
            if (mute.getMuteExpiration()!=null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                configuration.set("mutes."+mute.getMemberId()+".expiresIn", dateFormat.format(mute.getMuteExpiration()));
            }
        }

        yamlConfiguration.save(configuration,dataResource.getFile());

    }

}
