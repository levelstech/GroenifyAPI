package com.groenify.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MapperUtil {

    private static final Logger L = LoggerFactory.getLogger(MapperUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final CsvMapper CSV_MAPPER = new CsvMapper();

    private MapperUtil() {
    }

    public static <T> String writeJsonFromObject(
            final T object,
            final Class<T> clazz) {
        try {
            final String body = OBJECT_MAPPER.writerFor(clazz)
                    .writeValueAsString(object);

            L.trace("Writed object({}) => '{}'", clazz, body);
            return body;
        } catch (JsonProcessingException e) {
            L.error("Writing object to json from '{}'", clazz);
            return "{}";
        }
    }

    public static String readStringFromJSONFile(final String fileName) {
        try {
            final File file = new ClassPathResource(fileName).getFile();
            return Files.readString(file.toPath(), Charset.defaultCharset());

        } catch (IOException e) {
            L.error("Reading string from '{}'", fileName, e);
            return "";
        }
    }

    public static <T> List<T> readObjectFromCSVFile(
            final String fileName, final Class<T> clazz) {
        try {
            final CsvSchema schema = CsvSchema.emptySchema().withHeader();
            final File file = new ClassPathResource(fileName).getFile();
            final MappingIterator<T> readValues =
                    CSV_MAPPER.readerFor(clazz).with(schema).readValues(file);
            return readValues.readAll();
        } catch (Exception exception) {
            L.error("Reading objects to '{}' from '{}'",
                    clazz.getSimpleName(), fileName, exception);
            return Collections.emptyList();
        }
    }

    public static <T> T readObjectFromJsonString(
            final String jsonStr, final Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception exception) {
            L.error("Reading objects to '{}' from '{}'",
                    clazz.getSimpleName(), jsonStr, exception);
            return null;
        }
    }

    public static <T> List<T> readArrayFromJsonString(
            final String jsonStr, final Class<T> clazz) {
        final JsonArray array = JsonUtil.parseJsonArray(jsonStr);
        if (JsonUtil.isNullArray(array)) return new ArrayList<>();

        final List<T> parsedObjects = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            final JsonElement element = array.get(i);
            final T obj = readObjectFromJsonString(element.toString(), clazz);
            if (obj == null) return new ArrayList<>();

            parsedObjects.add(obj);
        }
        return parsedObjects;
    }

    public static Object mapStringToBasicObject(final String value) {
        if (value.equalsIgnoreCase("true")) return true;
        else if (value.equalsIgnoreCase("false")) return false;
        else if (value.startsWith("[") && value.endsWith("]"))
            return ListUtil.parseFromString(
                    value.substring(1, value.length() - 1));
        return value;
    }

    public static String mapBasicObjectToString(final Object answer) {
        return answer.toString();
    }
}
