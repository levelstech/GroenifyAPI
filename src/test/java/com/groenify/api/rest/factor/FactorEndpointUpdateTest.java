package com.groenify.api.rest.factor;

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
class FactorEndpointUpdateTest extends FactorEndpointById {

    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid(1)\","
                + "\"question\":\"Q(1)?\","
                + "\"description\":\"bb\"}");

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isTrue();
    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestFactor()))
                .andExpect(jsonPath("$.name", is("Factor-Wahid(1)")))
                .andExpect(jsonPath("$.question", is("Q(1)?")))
                .andExpect(jsonPathIdOfModelId(
                        "$.type.id", getTestFactor().getType()))
                .andExpect(jsonPath(
                        "$.type.name", is(getTestFactor().getType().getName())))
                .andExpect(jsonPath("$.description", is("bb")));

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isTrue();
    }

    @Test
    void getFactorInvalid() throws Exception {
        setFactorId(-1L);

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isTrue();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isFalse();

        setFactorId(getTestFactor().getId());
    }
}
