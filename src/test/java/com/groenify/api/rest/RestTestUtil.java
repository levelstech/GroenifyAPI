package com.groenify.api.rest;

import com.groenify.api.database.model.IdModel;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public final class RestTestUtil {
    private RestTestUtil() {
    }

    public static ResultMatcher jsonPathIdOfModelId(
            final String expression, final IdModel model) {
        return jsonPath(expression, equalTo(model.getId()), Long.class);
    }
}
