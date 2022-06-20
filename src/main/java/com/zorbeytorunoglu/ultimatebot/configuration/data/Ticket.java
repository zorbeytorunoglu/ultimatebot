package com.zorbeytorunoglu.ultimatebot.configuration.data;

import com.zorbeytorunoglu.ultimatebot.Bot;
import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private String owner;
    private String textChannelId;
    private Member member=null;
    private TextChannel textChannel=null;

    static final ArrayList<Ticket> tickets= new ArrayList<>();

    public Ticket() {}

    public Ticket(Member member, TextChannel textChannel) {
        this.member=member;
        this.owner=member.getId();
        this.textChannel=textChannel;
        this.textChannelId=textChannel.getId();
        tickets.add(this);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(String textChannelId) {
        this.textChannelId = textChannelId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    public void setTextChannel(TextChannel textChannel) {
        this.textChannel = textChannel;
    }

    public static void load(Bot bot, Configuration configuration) {
        if (configuration.getDefault("tickets")!=null) {
            if (!configuration.getSection("tickets").getKeys().isEmpty()) {
                configuration.getSection("tickets").getKeys().forEach(s -> {
                    tickets.add(loadTicket(bot, s, configuration));
                });
            }
        }
    }

    public static Ticket loadTicket(Bot bot, String textChannelId, Configuration configuration) {

        if (configuration.getDefault("tickets."+textChannelId)==null) return null;

        if (configuration.getString("tickets."+textChannelId+".ownerId")=="") return null;

        Ticket ticket=new Ticket();

        ticket.setOwner(configuration.getString("tickets."+textChannelId+".ownerId"));

        Member member=bot.getJda().getGuilds().get(0).retrieveMemberById(
                configuration.getString("tickets."+textChannelId+".ownerId")
        ).complete();

        if (member!=null) ticket.setMember(member);

        if (bot.getJda().getGuilds().get(0).getTextChannelById(textChannelId)!=null){
            ticket.setTextChannelId(textChannelId);
            ticket.setTextChannel(bot.getJda().getGuilds().get(0).getTextChannelById(textChannelId));
        }

        return ticket;

    }

    public static void save(DataHandler dataHandler) {

        if (getTickets().isEmpty()) return;

        for (Ticket ticket:getTickets()) {
            dataHandler.getConfiguration().set("tickets."+ticket.getTextChannelId()+".ownerId", ticket.getOwner());
        }

        try {
            dataHandler.getYamlConfiguration().save(dataHandler.getConfiguration(),dataHandler.getDataResource().getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
