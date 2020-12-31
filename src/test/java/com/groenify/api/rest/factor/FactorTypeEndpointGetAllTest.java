package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.RestTestUtil;
import com.groenify.api.service.factor.FactorTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";

    private static List<FactorType> TEST_TYPES;

    @Autowired
    private FactorTypeRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final FactorTypeEndpoint endpoint =
                new FactorTypeEndpoint(new FactorTypeService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\","
                        + "\"description\":\"aaaa\"}");
        final FactorType typeThanie = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Thanie\","
                        + "\"description\":\"aaaa\"}");
        final FactorType typeThalith = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Thalith\","
                        + "\"description\":\"aaaa\"}");
        TEST_TYPES = storeNews(List.of(typeWahid, typeThanie, typeThalith));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllFactorTypeValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "[{\"id\":1, \"name\":\"Type-Wahid\","
                + "\"description\":\"aaaa\"},"
                + "{\"id\":2, \"name\":\"Type-Thanie\","
                + "\"description\":\"aaaa\"},"
                + "{\"id\":3, \"name\":\"Type-Thalith\","
                + "\"description\":\"aaaa\"}]");
    }

    @Test
    void getAllFactorTypeValidateDatabaseValues() throws Exception {
        final FactorType typeWahid = TEST_TYPES.get(0);
        final FactorType typeThanie = TEST_TYPES.get(1);
        final FactorType typeThalith = TEST_TYPES.get(2);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(TEST_TYPES.size())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[0].id", typeWahid))
                .andExpect(jsonPath("$[0].name", is(typeWahid.getName())))
                .andExpect(jsonPath("$[0].description", is(typeWahid.getDescription())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[1].id", typeThanie))
                .andExpect(jsonPath("$[1].name", is(typeThanie.getName())))
                .andExpect(jsonPath("$[1].description", is(typeThanie.getDescription())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[2].id", typeThalith))
                .andExpect(jsonPath("$[2].name", is(typeThalith.getName())))
                .andExpect(jsonPath("$[2].description", is(typeThalith.getDescription())));
    }
}