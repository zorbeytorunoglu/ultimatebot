package com.zorbeytorunoglu.ultimatebot.configuration.datas;

import net.dv8tion.jda.api.entities.Member;

import java.util.Date;
import java.util.HashMap;

public class Mute {

    private final long memberId;
    private Date muteExpiration;
    static final HashMap<Long, Date> mutes=new HashMap<>();

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

    public static HashMap<Long, Date> getMutes() {
        return mutes;
    }

}
