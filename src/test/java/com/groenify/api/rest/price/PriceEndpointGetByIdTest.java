package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class PriceEndpointGetByIdTest extends PriceEndpointById {


    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        System.out.println(getEndpoint());

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":3,\"price\":100.0,"
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
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPrice()));
    }

    @Test
    void getFactorInvalid() throws Exception {
        setPriceId(-1L);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setPriceId(getTestPrice().getId());
    }
}
