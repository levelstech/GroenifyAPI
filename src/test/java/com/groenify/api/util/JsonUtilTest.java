package com.groenify.api.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonUtilTest {

    @Test
    void getArrayFromElementWithDefault() {
        final JsonArray defaultArray = new JsonArray();
        final JsonElement element = new JsonPrimitive("key");
        defaultArray.add(element);


        final JsonArray array =
                JsonUtil.getArrayFromElement(new JsonArray());
        Assertions.assertThat(array).isEmpty();

        final JsonArray invalidArray =
                JsonUtil.getArrayFromElement(new JsonObject(), defaultArray);
        Assertions.assertThat(invalidArray).isNotEmpty();
        Assertions.assertThat(invalidArray).contains(element);
    }

    @Test
    void getArrayFromElement() {
        final JsonArray array = JsonUtil.getArrayFromElement(new JsonArray());
        Assertions.assertThat(array).isEmpty();

        final JsonArray invalidArray =
                JsonUtil.getArrayFromElement(new JsonObject());
        Assertions.assertThat(invalidArray).isNotEmpty();
        Assertions.assertThat(JsonUtil.isNullArray(invalidArray)).isTrue();
    }

    @Test
    void getObjectFromElementWithDefault() {
        final JsonObject defaultObject = new JsonObject();
        defaultObject.addProperty("key", "value");

        final JsonObject object =
                JsonUtil.getObjectFromElement(new JsonObject());
        Assertions.assertThat(object.keySet()).isEmpty();

        final JsonObject invalidObject =
                JsonUtil.getObjectFromElement(new JsonArray(), defaultObject);
        Assertions.assertThat(invalidObject.keySet()).isNotEmpty();
        Assertions.assertThat(invalidObject.has("key")).isTrue();

        final JsonElement key = invalidObject.get("key");
        Assertions.assertThat(key.isJsonPrimitive()).isTrue();

        final JsonPrimitive primitive = key.getAsJsonPrimitive();

        Assertions.assertThat(primitive.isString()).isTrue();
        Assertions.assertThat(primitive.getAsString()).isEqualTo("value");
    }

    @Test
    void getObjectFromElement() {
        final JsonObject object =
                JsonUtil.getObjectFromElement(new JsonObject());
        Assertions.assertThat(object.keySet()).isEmpty();

        final JsonObject invalidObject =
                JsonUtil.getObjectFromElement(new JsonArray());
        Assertions.assertThat(object.keySet()).isEmpty();
        Assertions.assertThat(JsonUtil.isNullObject(invalidObject)).isTrue();
    }

    @Test
    void parseElement() {
        final JsonElement primitive = JsonUtil.parseElement("1");
        Assertions.assertThat(primitive.isJsonPrimitive()).isTrue();

        final JsonElement object = JsonUtil.parseElement("{}");
        Assertions.assertThat(object.isJsonObject()).isTrue();

        final JsonElement array = JsonUtil.parseElement("[]");
        Assertions.assertThat(array.isJsonArray()).isTrue();
    }

    @Test
    void parseJsonObject() {
        final JsonObject invalidObject = JsonUtil.parseJsonObject("");
        Assertions.assertThat(JsonUtil.isNullObject(invalidObject)).isTrue();

        final JsonObject objectEmpty = JsonUtil.parseJsonObject("{}");
        Assertions.assertThat(objectEmpty.keySet()).isEmpty();

        final JsonObject object = JsonUtil.parseJsonObject(
                "{\"key_1\":1,\"key_2\":\"hallo\"}");
        Assertions.assertThat(object.keySet())
                .containsExactly("key_1", "key_2");
        Assertions.assertThat(object.get("key_1").isJsonPrimitive()).isTrue();
        Assertions.assertThat(object.get("key_2").isJsonPrimitive()).isTrue();

        final JsonPrimitive key1 = object.get("key_1").getAsJsonPrimitive();
        final JsonPrimitive key2 = object.get("key_2").getAsJsonPrimitive();

        Assertions.assertThat(key1.isNumber()).isTrue();
        Assertions.assertThat(key1.getAsInt()).isEqualTo(1);
        Assertions.assertThat(key2.isString()).isTrue();
        Assertions.assertThat(key2.getAsString()).isEqualTo("hallo");
    }

    @Test
    void parseJsonArray() {
        final JsonArray invalidArray = JsonUtil.parseJsonArray("");
        Assertions.assertThat(JsonUtil.isNullArray(invalidArray)).isTrue();

        final JsonArray arrayEmpty = JsonUtil.parseJsonArray("[]");
        Assertions.assertThat(arrayEmpty).isEmpty();

        final JsonArray array = JsonUtil.parseJsonArray(
                "[\"key_1\",\"key_2\"]");
        Assertions.assertThat(array)
                .containsExactly(new JsonPrimitive("key_1"),
                        new JsonPrimitive("key_2"));
        Assertions.assertThat(
                array.contains(new JsonPrimitive("key_1"))).isTrue();
        Assertions.assertThat(
                array.contains(new JsonPrimitive("key_2"))).isTrue();
    }

    @Test
    void isNullObject() {
        final JsonObject invalidObject = JsonUtil.parseJsonObject("");
        Assertions.assertThat(JsonUtil.isNullObject(invalidObject)).isTrue();
    }

    @Test
    void isNullArray() {
        final JsonArray invalidArray = JsonUtil.parseJsonArray("");
        Assertions.assertThat(JsonUtil.isNullArray(invalidArray)).isTrue();
    }
}