package com.groenify.api.rest;

import com.groenify.api.TestModelCreatorUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.List;

public final class TestRestObjectGetterUtil {
    private static final Logger L =
            LoggerFactory.getLogger(TestModelCreatorUtil.class);
    private static final String FILENAME =
            "src/test/javascript/builder/object_builder.js";
    private static boolean isStarted = false;
    private static ScriptEngine engine;
    private static FileReader fr;

    private TestRestObjectGetterUtil() {
    }

    private static Object[] parseArguments(
            final Class<?> clazz,
            final String type,
            final Object[] args) {

        final List<Object> objects =
                Lists.newArrayList(clazz.getSimpleName(), type);
        objects.addAll(List.of(args));
        return objects.toArray();
    }

    public static void startUp() {
        if (isStarted) return;

        try {
            engine = new ScriptEngineManager().getEngineByName("GraalJS");
            engine.put("engine", engine);
            fr = new FileReader(FILENAME, Charset.defaultCharset());
            engine.eval(fr);
            isStarted = true;
        } catch (Exception e) {
            L.error("Could not start up", e);
        }
    }

    public static void tearDown() {
        try {
            if (isStarted) fr.close();
        } catch (Exception e) {
            L.error("Could not tear down", e);
        }
    }

    public static String getJsonResponseObject(
            final Class<?> clazz,
            final Object... args) {
        return getJsonObject(clazz, "res", args);
    }

    public static String getJsonObject(
            final Class<?> clazz,
            final String type,
            final Object... args) {

        final Object[] objects = parseArguments(clazz, type, args);
        try {
            startUp();
            final Object response = ((Invocable) engine).
                    invokeFunction("get", objects);
            return String.valueOf(response);
        } catch (Exception e) {
            L.error("Could get {} with args: {} ", clazz, objects, e);
            return "";
        }

    }

}
