package com.zorbeytorunoglu.ultimatebot.utils;

import com.zorbeytorunoglu.ultimatebot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.List;

public class BotUtils {

    public static boolean fileExists(String fileName) {
        return false;
    }

    public static Activity getActivity(Bot bot) {

        String activity=bot.getSettingsHandler().getSettings().getActivity();
        String activityLabel=bot.getSettingsHandler().getSettings().getActivityLabel();

        Activity act=null;

        switch (activity) {
            case "watching":
                act=Activity.watching(activityLabel);
                break;
            case "listening":
                act=Activity.listening(activityLabel);
                break;
            case "competing":
                act=Activity.competing(activityLabel);
                break;
            default:
                act=Activity.playing(activityLabel);
        }

        return act;

    }

    public static Color getColor(String color) {
        switch (color) {
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            case "RED":
                return Color.RED;
            case "PINK":
                return Color.PINK;
            case "CYAN":
                return Color.CYAN;
            case "GRAY":
                return Color.GRAY;
            case "DARK_GREY":
                return Color.DARK_GRAY;
            case "GREEN":
                return Color.GREEN;
            case "MAGENTA":
                return Color.MAGENTA;
            case "WHITE":
                return Color.WHITE;
            case "LIGHT_GRAY":
                return Color.LIGHT_GRAY;
            default:
                return Color.ORANGE;
        }
    }

    public static MessageEmbed getEmbed(String title, String message, Color color) {
        if (title.equals("none")) {
            return new EmbedBuilder().setDescription(message).setColor(color).build();
        } else {
            return new EmbedBuilder().setTitle(title).setDescription(message).setColor(color).build();
        }
    }

    public static MessageEmbed getEmbed(String title, String message, String color) {
        if (title.equals("none")) {
            return new EmbedBuilder().setDescription(message).setColor(getColor(color)).build();
        } else {
            return new EmbedBuilder().setTitle(title).setDescription(message).setColor(getColor(color)).build();
        }
    }

    public static Member getMember(Mentions mentions) {
        if (mentions.getMembers().isEmpty()) return null;
        return mentions.getMembers().get(0);
    }

    public static Member getMember(Guild guild, String id) {
        if (!StringUtils.isNumeric(id)) return null;
        Member member;
        if (guild.getMemberById(id)==null) {
            try {
                member = guild.retrieveMemberById(id).complete();
            } catch (Exception e) {
                member=null;
            }
        } else {
            member=guild.getMemberById(id);
        }
        return member;
    }

    public static Member getMember(Guild guild, Mentions mentions, String arg) {
        return getMember(mentions)==null ? getMember(guild,arg) : getMember(mentions);
    }

    public static Role getRole(Mentions mentions) {
        return mentions.getRoles().isEmpty() ? null : mentions.getRoles().get(0);
    }

    public static Role getRole(Guild guild, String id) {
        return !StringUtils.isNumeric(id) ? null : guild.getRoleById(id);
    }

    public static void givePermission(Bot bot, String permission, Member member) {
        List<String> permissions=bot.getPermissionHandler().getUserPermissions().get(permission);
        if (!permissions.contains(member.getId())) permissions.add(member.getId());
        bot.getPermissionHandler().getConfiguration().set("commands."+permission+".users", permissions);
        bot.getPermissionHandler().saveFile();
    }

    public static void takePermission(Bot bot, String permission, Member member) {
        List<String> permissions=bot.getPermissionHandler().getUserPermissions().get(permission);
        if (permissions.contains(member.getId())) permissions.remove(member.getId());
        bot.getPermissionHandler().getConfiguration().set("commands."+permission+".users", permissions);
        bot.getPermissionHandler().saveFile();
    }

    public static void givePermission(Bot bot, String permission, Role role) {
        List<String> permissions=bot.getPermissionHandler().getRolePermissions().get(permission);
        if (!permissions.contains(role.getId())) permissions.add(role.getId());
        bot.getPermissionHandler().getConfiguration().set("commands."+permission+".roles", permissions);
        bot.getPermissionHandler().saveFile();
    }

    public static void takePermission(Bot bot, String permission, Role role) {
        List<String> permissions=bot.getPermissionHandler().getRolePermissions().get(permission);
        if (permissions.contains(role.getId())) permissions.remove(role.getId());
        bot.getPermissionHandler().getConfiguration().set("commands."+permission+".roles", permissions);
        bot.getPermissionHandler().saveFile();
    }

    public static GuildChannel getGuildChannel(Mentions mentions) {
        if (mentions.getChannels().isEmpty()) return null;
        return mentions.getChannels().get(0);
    }

    public static GuildChannel getGuildChannel(Guild guild, String id) {
        if (!StringUtils.isNumeric(id)) return null;
        return guild.getGuildChannelById(id)==null ? null : guild.getGuildChannelById(id);
    }

    public static GuildChannel getGuildChannel(Guild guild, Mentions mentions, String id) {
        return getGuildChannel(mentions)==null ? getGuildChannel(guild,id) : getGuildChannel(mentions);
    }

}
