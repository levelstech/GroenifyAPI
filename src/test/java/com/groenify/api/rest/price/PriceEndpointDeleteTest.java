package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class PriceEndpointDeleteTest extends PriceEndpointById {

    @Test
    void deletePriceDeleteValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(getRepository()
                .existsById(getPriceId()))
                .isFalse();
    }

    @Test
    void deletePriceDeleteValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(getRepository()
                .existsById(getPriceId()))
                .isFalse();
    }

    @Test
    void deletePriceDeleteInvalid() throws Exception {
        final Long originalId =getPriceId();
        setPriceId(-1L);
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());

        Assertions.assertThat(getRepository()
                .existsById(originalId))
                .isTrue();
        setPriceId(getTestPrice().getId());
    }
}
