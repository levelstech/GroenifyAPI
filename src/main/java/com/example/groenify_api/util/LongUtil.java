package com.example.groenify_api.util;

public class LongUtil {

    private LongUtil() {}

    public static Long parseOrDefault(final String string) {
        try {
            return Long.parseLong(string);
        } catch (Exception ignored) {
            return 0L;
        }
    }
}
