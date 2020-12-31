package com.groenify.api.rest.epole;

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
class EPoleBrandEndpointUpdateTest extends EPoleBrandEndpointById {

    @Test
    void putEPoleBrandUpdateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Brand-Wahid(1)\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Brand-Wahid(1)\"}");
    }

    @Test
    void putEPoleBrandUpdateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Brand-Wahid(1)\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Brand-Wahid(1)")));

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid")).isFalse();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid(1)")).isTrue();

        Assertions.assertThat(
                getTestBrand().getName()).isEqualTo("Brand-Wahid(1)");
    }

    @Test
    void putEPoleBrandUpdateInvalid() throws Exception {
        setBrandId(-1L);
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Brand-Wahid(1)\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid")).isTrue();
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid(1)")).isFalse();
        setBrandId(getTestBrand().getId());
    }
}
