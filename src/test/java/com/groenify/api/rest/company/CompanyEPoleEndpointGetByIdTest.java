package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEPoleEndpointGetByIdTest extends CompanyEPoleEndpointById {

    @Test
    void getCompanyEPolesByIdValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":7,\"company\":"
                + "{\"id\":5,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":121.0,\"epole\":"
                + "{\"id\":5,\"brand\":{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}");
    }

    @Test
    void getCompanyEPolesByIdValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$.epole.id", getTestPole().getEPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$.company.id", getTestPole().getCompany()))
                .andExpect(jsonPath(
                        "$.base_price", is(getTestPole().getBasePrice())));
    }

    @Test
    void getCompanyEPoleByIdInvalid() throws Exception {
        setPoleId(-1L);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setPoleId(getTestPole().getId());
    }
}
