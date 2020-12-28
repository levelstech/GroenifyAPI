package com.example.groenify_api.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public final class JsonUtil {

    private JsonUtil() {}

    private static final String NULL_KEY = "$null_key";

    private static final JsonObject NULL_OBJECT = new JsonObject();
    private static final JsonArray NULL_ARRAY = new JsonArray();

    static {
        NULL_OBJECT.addProperty(NULL_KEY, true);
        NULL_ARRAY.add(NULL_KEY);
    }

    static JsonArray getArrayFromElement(
            final JsonElement element,
            final JsonArray defaultElement) {
        if (!element.isJsonArray()) return defaultElement;
        return element.getAsJsonArray();
    }

    static JsonArray getArrayFromElement(
            final JsonElement element) {
        return getArrayFromElement(element, NULL_ARRAY);
    }

    static JsonObject getObjectFromElement(
            final JsonElement element,
            final JsonObject defaultElement) {
        if (!element.isJsonObject()) return defaultElement;
        return element.getAsJsonObject();
    }

    static JsonObject getObjectFromElement(
            final JsonElement element) {
        return getObjectFromElement(element, NULL_OBJECT);
    }

    public static JsonElement parseElement(final String var) {
        return JsonParser.parseString(var);
    }

    public static JsonObject parseJsonObject(final String var1) {
        final JsonElement element = JsonParser.parseString(var1);

        return JsonUtil.getObjectFromElement(element);
    }

    public static JsonArray parseJsonArray(final String var1) {
        final JsonElement element = JsonParser.parseString(var1);

        return JsonUtil.getArrayFromElement(element);
    }

    public static Boolean isNullObject(final JsonObject object) {
        return object.has(NULL_KEY);
    }

    public static Boolean isNullArray(final JsonArray object) {
        return object.contains(new JsonPrimitive(NULL_KEY));
    }

}
