package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEPoleEndpointUpdateTest extends CompanyEPoleEndpointById {

    @Test
    void putCompanyUpdateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonTestUtil.test(resBody, "{\"id\":7,\"company\":"
                + "{\"id\":5,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":50.51,\"epole\":"
                + "{\"id\":5,\"brand\":{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}");
    }

    @Test
    void putCompanyUpdateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$.epole.id", getTestPole().getEPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$.company.id", getTestPole().getCompany()))
                .andExpect(jsonPath(
                        "$.base_price", is(getTestPole().getBasePrice())));

        Assertions.assertThat(getTestPole().getBasePrice()).isEqualTo(50.51d);
    }

    @Test
    void putCompanyUpdateInvalid() throws Exception {
        setPoleId(-1L);
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(
                getTestPole().getBasePrice()).isNotEqualTo(50.51d);
        setPoleId(getTestPole().getId());
    }
}
