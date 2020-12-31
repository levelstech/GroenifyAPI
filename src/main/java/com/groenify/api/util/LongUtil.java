package com.groenify.api.util;

public final class LongUtil {

    private LongUtil() {
    }

    public static Long parseOrDefault(final String string) {
        try {
            return Long.parseLong(string);
        } catch (Exception ignored) {
            return 0L;
        }
    }
}
