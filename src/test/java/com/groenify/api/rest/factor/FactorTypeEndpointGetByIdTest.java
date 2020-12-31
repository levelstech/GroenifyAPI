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

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointGetByIdTest extends FactorTypeEndpointById {

    @Test
    void getFactorTypeByIdValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Type-Wahid(1)\","
                + "\"description\":\"aaaa\"}");
    }

    @Test
    void getFactorTypeByIdValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestType()))
                .andExpect(jsonPath("$.name", is(getTestType().getName())))
                .andExpect(jsonPath(
                        "$.description", is(getTestType().getDescription())));
    }

    @Test
    void getFactorTypeByIdNotFound() throws Exception {
        setTypeId(-1L);
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setTypeId(getTestType().getId());
    }
}
