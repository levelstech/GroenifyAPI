package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.rest.price.__model.FactorAnswerPriceResMo;
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

        final String resObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerPriceResMo.class, "1");
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"price\":80.00 } "))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, resObj);
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
