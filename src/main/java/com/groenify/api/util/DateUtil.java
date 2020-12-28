package com.groenify.api.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtil {

    public static final String ISO_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private DateUtil() {
    }

    public static String toIsoNoMillis(final @Nullable Date date) {
        if (date == null) return null;

        return toFormat(date, ISO_NO_MILLIS);
    }

    private static String toFormat(
            final @NonNull Date date,
            final String format) {
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        final DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static Date fromIsoNoMillis(final String dateString) {
        return fromFormat(dateString, ISO_NO_MILLIS);
    }

    public static Date fromIsoNoMillis(
            final String dateString,
            final Date defaultDate) {
        return fromFormat(dateString, ISO_NO_MILLIS, defaultDate);
    }

    public static Date fromFormat(
            final String dateString,
            final String format) {
        final DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return df.parse(dateString);
        } catch (Exception eParam) {
            return null;
        }
    }

    public static Date fromFormat(
            final String dateString, final String format,
            final Date defaultDate) {
        final DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return df.parse(dateString);
        } catch (ParseException eParam) {
            return defaultDate;
        }
    }

    public static Date now() {
        return new Date();
    }

}

