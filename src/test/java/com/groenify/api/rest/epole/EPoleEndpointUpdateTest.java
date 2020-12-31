package com.groenify.api.rest.epole;

import com.groenify.api.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class EPoleEndpointUpdateTest extends EPoleEndpointById {

    @Test
    void getEFromPoleValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid(1)\","
                                + "\"description\":\"(1)\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"type\":\"Pole-Wahid(1)\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + " \"description\":\"(1)\"}");

        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid(1)")).isTrue();
    }

    @Test
    void getEPoleValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid(1)\","
                                + "\"description\":\"(1)\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPole()))
                .andExpect(jsonPath("$.type", is("Pole-Wahid(1)")))
                .andExpect(jsonPath("$.description", is("(1)")))
                .andExpect(jsonPathIdOfModelId(
                        "$.brand.id", getTestPole().getBrand()))
                .andExpect(jsonPath(
                        "$.brand.name",
                        is(getTestPole().getBrand().getName())));

        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid(1)")).isTrue();
    }

    @Test
    void getEPoleInvalid() throws Exception {
        setPoleId(-1L);

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pole-Wahid(1)\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isTrue();
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid(1)")).isFalse();

        setPoleId(getTestPole().getId());
    }
}
