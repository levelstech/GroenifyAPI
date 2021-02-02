package com.groenify.api.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public final class LoadOrderFetcher {

    private LoadOrderFetcher() {
    }

    private static final Logger L =
            LoggerFactory.getLogger(LoadOrderFetcher.class);

    private static Integer getOrderFromMethod(final Method method) {
        final LoadOrder loadOrderAnnotation =
                method.getAnnotation(LoadOrder.class);
        if (loadOrderAnnotation == null) {
            L.warn("Annotation 'LoadOrder' was not found for method '{}'",
                    method);
            L.warn("please check the implementation of '{}'",
                    method.getDeclaringClass());
            return LoadOrder.DEFAULT_VALUE;
        }

        return loadOrderAnnotation.value();
    }

    private static Integer getOrderFromClass(final Class<?> clazz) {
        try {
            final Method method = clazz.getMethod("loadOnReady");
            return getOrderFromMethod(method);
        } catch (NoSuchMethodException exc) {
            L.error("Method 'loadOnReady()' was not found for on a '{}' class",
                    ReadyEventLoader.class);
            L.error("please check the implementation of '{}'", clazz);
            return LoadOrder.DEFAULT_VALUE;
        }
    }

    public static Integer getOrder(final ReadyEventLoader loader) {
        final Class<?> c = loader.getClass();
        return getOrderFromClass(c);
    }
}
