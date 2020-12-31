package com.groenify.api.rest.epole;

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
class EPoleBrandEndpointGetByIdTest extends EPoleBrandEndpointById {

    @Test
    void getEPoleBrandByIdValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Brand-Wahid\"}");
    }

    @Test
    void getEPoleBrandByIdValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestBrand()))
                .andExpect(jsonPath("$.name", is(getTestBrand().getName())));
    }

    @Test
    void getEPoleBrandByIdNotFound() throws Exception {
        setBrandId(-1L);
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        setBrandId(getTestBrand().getId());
    }
}
