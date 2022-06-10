package com.zorbeytorunoglu.ultimatebot.configuration.datas;

import java.util.ArrayList;
import java.util.Date;

public class Mute {
    private final Long memberId;
    private Date muteExpiration=null;
    private String reason=null;
    static final ArrayList<Mute> mutes=new ArrayList<>();
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
}
