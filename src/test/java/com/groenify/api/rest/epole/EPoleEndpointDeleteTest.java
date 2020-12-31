package com.groenify.api.rest.epole;

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
class EPoleEndpointDeleteTest extends EPoleEndpointById {

    @Test
    void deleteEFromPoleValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleInvalid() throws Exception {
        setPoleId(-1L);

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());
        Assertions.assertThat(getRepository().existsByTypeIgnoreCase(
                "Pole-Wahid")).isTrue();
        setPoleId(getTestPole().getId());
    }
}
