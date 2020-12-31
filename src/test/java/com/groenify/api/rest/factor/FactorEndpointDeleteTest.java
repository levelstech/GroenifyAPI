package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorEndpointDeleteTest extends FactorEndpointById {

    @Test
    void deleteEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
    }

    @Test
    void deleteFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
    }

    @Test
    void deleteFactorInvalid() throws Exception {
        setFactorId(-1L);

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Factor-Wahid")).isTrue();
        setFactorId(getTestFactor().getId());
    }
}
