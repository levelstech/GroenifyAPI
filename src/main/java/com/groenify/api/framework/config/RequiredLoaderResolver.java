package com.groenify.api.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public final class RequiredLoaderResolver {

    private RequiredLoaderResolver() {
    }

    private static final Logger L =
            LoggerFactory.getLogger(RequiredLoaderResolver.class);

    private static Boolean getRequiredFromMethod(final Method method) {
        final RequiredLoader loadOrderAnnotation =
                method.getAnnotation(RequiredLoader.class);
        if (loadOrderAnnotation == null) {
            L.warn("Annotation 'LoadOrder' was not found for method '{}'",
                    method);
            L.warn("please check the implementation of '{}'",
                    method.getDeclaringClass());
            return RequiredLoader.DEFAULT_VALUE;
        }

        return RequiredLoader.ACTIVE;
    }

    private static Boolean getRequiredFromClass(final Class<?> clazz) {
        try {
            final Method method = clazz.getMethod("loadOnReady");
            return getRequiredFromMethod(method);
        } catch (NoSuchMethodException exc) {
            L.error("Method 'loadOnReady()' was not found for on a '{}' class",
                    ReadyEventLoader.class);
            L.error("please check the implementation of '{}'", clazz);
            return RequiredLoader.DEFAULT_VALUE;
        }
    }

    public static Boolean isRequiredLoader(final ReadyEventLoader loader) {
        final Class<?> c = loader.getClass();
        return getRequiredFromClass(c);
    }
}
