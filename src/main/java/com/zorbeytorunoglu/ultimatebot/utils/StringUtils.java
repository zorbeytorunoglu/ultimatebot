package com.zorbeytorunoglu.ultimatebot.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils {

    public static boolean isNumeric(String string) {
        return NumberUtils.isParsable(string);
    }

}
