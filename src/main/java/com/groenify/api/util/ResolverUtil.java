package com.groenify.api.util;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

public final class ResolverUtil {

    private static String findInPath(final WebRequest var1, final String var2) {
//        noinspection unchecked
        final Map<String, String> uriTemplateVars = (Map<String, String>)
                var1.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, 0);
        if (uriTemplateVars != null && uriTemplateVars.containsKey(var2)) {
            return uriTemplateVars.get(var2);
        }
        return "";
    }

    public static Long findLongInPath(final WebRequest var1, final String var2) {
        final String userInput = findInPath(var1, var2);
        return LongUtil.parseOrDefault(userInput);
    }

}
