package com.groenify.api.rest.epole;

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
class EPoleEndpointGetByIdTest extends EPoleEndpointById {

    @Test
    void getEFromPoleValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"type\":\"Pole-Wahid\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + " \"description\":\"aa\"}");
    }

    @Test
    void getEPoleValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPole()))
                .andExpect(jsonPath("$.type", is(getTestPole().getType())))
                .andExpect(jsonPath(
                        "$.description", is(getTestPole().getDescription())))
                .andExpect(jsonPathIdOfModelId(
                        "$.brand.id", getTestPole().getBrand()))
                .andExpect(jsonPath(
                        "$.brand.name",
                        is(getTestPole().getBrand().getName())));
    }

    @Test
    void getEPoleInvalid() throws Exception {
        setPoleId(-1L);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setPoleId(getTestPole().getId());
    }
}
