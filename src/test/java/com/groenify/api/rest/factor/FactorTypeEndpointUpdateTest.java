package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointUpdateTest extends FactorTypeEndpointById {

    @Test
    void putFactorTypeUpdateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1)\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Type-Wahid(1)\","
                + "\"description\":\"aaaa\"}");
    }

    @Test
    void putFactorTypeUpdateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1)\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Type-Wahid(1)")))
                .andExpect(jsonPath("$.description", is("aaaa")));
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Type-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Type-Wahid(1)")).isTrue();

        Assertions.assertThat(
                getTestType().getName()).isEqualTo("Type-Wahid(1)");
        Assertions.assertThat(getTestType().getDescription()).isEqualTo("aaaa");
    }

    @Test
    void putFactorTypeUpdateInvalid() throws Exception {
        setTypeId(-1L);
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1),"
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Type-Wahid")).isTrue();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Type-Wahid(1)")).isFalse();
        setTypeId(getTestType().getId());
    }
}
