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

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class EPoleBrandEndpointDeleteTest extends EPoleBrandEndpointById {


    @Test
    void deleteEPoleBrandDeleteValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleBrandDeleteValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleBrandDeleteInvalid() throws Exception {
        setBrandId(-1L);
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository().existsByNameIgnoreCase(
                "Brand-Wahid")).isTrue();
        setBrandId(getTestBrand().getId());
    }
}
