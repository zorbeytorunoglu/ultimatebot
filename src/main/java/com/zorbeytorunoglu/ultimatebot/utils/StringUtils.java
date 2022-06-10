package com.zorbeytorunoglu.ultimatebot.utils;

import com.zorbeytorunoglu.ultimatebot.configuration.messages.Messages;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static boolean isNumeric(String string) {
        return NumberUtils.isParsable(string);
    }

    public static String formatDuration(Messages messages, long totalSecs) {

        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;

        StringBuilder stringBuilder=new StringBuilder();

        if (hours>0) {
            if (hours==1) {
                stringBuilder.append(hours+" "+messages.getHour());
            } else {
                stringBuilder.append(hours+" "+messages.getHours());
            }
        }

        if (minutes>0) {
            if (minutes==1) {
                if (stringBuilder.length()!=0) {
                    stringBuilder.append(" "+minutes+" "+messages.getMinute());
                } else {
                    stringBuilder.append(minutes+" "+messages.getMinute());
                }
            } else {
                if (stringBuilder.length()!=0) {
                    stringBuilder.append(" "+minutes+" "+messages.getMinutes());
                } else {
                    stringBuilder.append(minutes+" "+messages.getMinutes());
                }
            }
        }

        return stringBuilder.toString();

    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
        return sdf.format(date);
    }

}
