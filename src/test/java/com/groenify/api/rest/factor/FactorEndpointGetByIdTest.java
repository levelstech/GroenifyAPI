package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorEndpointGetByIdTest extends FactorEndpointById {

    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid\","
                + "\"question\":\"Q?\","
                + "\"description\":\"aa\"}");
    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {
        final String name = getTestFactor().getName();
        final String question = getTestFactor().getQuestion();
        final String description = getTestFactor().getDescription();

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestFactor()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.question", is(question)))
                .andExpect(jsonPathIdOfModelId(
                        "$.type.id", getTestFactor().getType()))
                .andExpect(jsonPath(
                        "$.type.name", is(getTestFactor().getType().getName())))
                .andExpect(jsonPath("$.description", is(description)));
    }

    @Test
    void getFactorInvalid() throws Exception {
        setFactorId(-1L);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setFactorId(getTestFactor().getId());
    }
}
