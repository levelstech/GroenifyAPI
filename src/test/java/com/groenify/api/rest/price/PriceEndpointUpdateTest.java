package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class PriceEndpointUpdateTest extends PriceEndpointById {


    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"price\":80.00 } "))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":3,\"price\":80.0,"
                + "\"factor_answer\":{\"id\":3,"
                + "\"factor\":{\"id\":2,"
                + "\"type\":{\"id\":10,\"name\":\"Type-Wahid\","
                + "\"description\":null},"
                + "\"name\":\"Factor-Wahid1\",\"question\":\"Q11?\","
                + "\"description\":\"dd\"},\"type\":6,\"answer\":true},"
                + "\"company_epole\":{\"id\":2,\"company\":"
                + "{\"id\":2,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":121.0,\"epole\":{\"id\":2,\"brand\":"
                + "{\"id\":2,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}}");
    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"price\":80.00 } "))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPrice()))
                .andExpect(jsonPath("$.price", Matchers.is(80.d)));
    }

    @Test
    void getFactorInvalid() throws Exception {
        setPriceId(-1L);

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"price\":80.00 } "))
                .andExpect(status().isNotFound());
        setPriceId(getTestPrice().getId());
    }
}
