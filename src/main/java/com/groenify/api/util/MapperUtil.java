package com.groenify.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class MapperUtil {

    private static final Logger L = LoggerFactory.getLogger(MapperUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T readObject(final String jsonStr, final Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception exception) {
            L.error("Reading objects to '{}' from '{}'",
                    clazz.getSimpleName(), jsonStr, exception);
            return null;
        }
    }

    public static <T> List<T> readArray(
            final String jsonStr, final Class<T> clazz) {
        final JsonArray array = JsonUtil.parseJsonArray(jsonStr);
        if (JsonUtil.isNullArray(array)) return new ArrayList<>();

        final List<T> parsedObjects = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            final JsonElement element = array.get(i);
            final T parsed = readObject(element.toString(), clazz);
            if (parsed == null) return new ArrayList<>();

            parsedObjects.add(parsed);
        }
        return parsedObjects;
    }
}
