package com.muti.util;

/**
 * Desciption
 * Create By  li.bo
 * CreateTime 2018/10/17 17:19
 * UpdateTime 2018/10/17 17:19
 */
public class StringUtil {

    public static <T> T notNull(T object, String message, Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        }
        return object;
    }

    public static <T extends CharSequence> T notBlank(T chars, String message, Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (StringUtil.isBlank(chars)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    public static boolean isBlank(Object _baseSt) {

        boolean b = false;

        if (_baseSt == null) {
            b = true;
        } else {

            String s = _baseSt.toString().trim();

            if (s.length() > 0) {
                b = false;
            } else {
                b = true;
            }
        }
        return b;

    }
}
