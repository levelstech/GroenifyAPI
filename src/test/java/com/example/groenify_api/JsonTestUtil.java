package com.example.groenify_api;

import com.example.groenify_api.util.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.assertj.core.api.Assertions;

public final class JsonTestUtil {

    public static void test(
            final String b1, final String b2) {
        final JsonElement e1 = JsonUtil.parseElement(b1);
        final JsonElement e2 = JsonUtil.parseElement(b2);
        test(e1, e2, false);
    }

    public static void test(
            final String b1, final String b2,
            final Boolean testEqual) {
        final JsonElement e1 = JsonUtil.parseElement(b1);
        final JsonElement e2 = JsonUtil.parseElement(b2);
        test(e1, e2, testEqual);
    }

    public static void test(
            final JsonElement b1, final JsonElement b2,
            final Boolean testEqual) {
        if (b1.isJsonArray() && b2.isJsonArray()) {
            test(b1.getAsJsonArray(), b2.getAsJsonArray(), testEqual);
        } else if (b1.isJsonObject() && b2.isJsonObject()) {
            test(b1.getAsJsonObject(), b2.getAsJsonObject(), testEqual);
        } else if (b1.isJsonPrimitive() && b2.isJsonPrimitive()) {
            test(b1.getAsJsonPrimitive(), b2.getAsJsonPrimitive(), testEqual);
        }
        Assertions.assertThat(b1.getClass()).isEqualTo(b2.getClass());
    }

    public static void test(
            final JsonObject o1, final JsonObject o2,
            final Boolean testEqual) {
        Assertions.assertThat(o1.keySet()).isEqualTo(o2.keySet());
        for (final var key : o1.keySet())
            test(o1.get(key), o2.get(key), testEqual);

    }

    public static void test(
            final JsonPrimitive o1, final JsonPrimitive o2,
            final Boolean testEqual) {
        Assertions.assertThat(o1.isBoolean()).isEqualTo(o2.isBoolean());
        Assertions.assertThat(o1.isNumber()).isEqualTo(o2.isNumber());
        Assertions.assertThat(o1.isString()).isEqualTo(o2.isString());


        if (testEqual) testPrimitive(o1, o2);
    }

    public static void testPrimitive(
            final JsonPrimitive o1,
            final JsonPrimitive o2) {

        if (o1.isBoolean() && o2.isBoolean()) {
            Assertions.assertThat(o1.getAsBoolean())
                    .isEqualTo(o2.getAsBoolean());
        } else if (o1.isNumber() && o2.isNumber()) {
            Assertions.assertThat(o1.getAsNumber())
                    .isEqualTo(o2.getAsNumber());
        } else if (o1.isString() && o2.isString()) {
            Assertions.assertThat(o1.getAsString())
                    .isEqualTo(o2.getAsString());
        }
    }

    public static void test(
            final JsonArray o1, final JsonArray o2,
            final Boolean testEqual) {
        Assertions.assertThat(o1.size()).isEqualTo(o2.size());
        for (int i = 0; i < o2.size(); i++)
            test(o1.get(i), o2.get(i), testEqual);

    }
}
