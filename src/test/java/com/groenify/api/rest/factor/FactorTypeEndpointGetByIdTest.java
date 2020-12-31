package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointGetByIdTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static FactorType testType;
    private static Long typeId;

    @Autowired
    private FactorTypeRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + typeId;
    }

    protected void setUpMock() {
        final FactorTypeEndpoint endpoint =
                new FactorTypeEndpoint(new FactorTypeService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\","
                        + "\"description\":\"aaaa\"}");
        testType = storeNew(typeWahid);
        typeId = testType.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getFactorTypeByIdValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Type-Wahid(1)\","
                + "\"description\":\"aaaa\"}");
    }

    @Test
    void getFactorTypeByIdValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", testType))
                .andExpect(jsonPath("$.name", is(testType.getName())))
                .andExpect(jsonPath("$.description", is(testType.getDescription())));
    }

    @Test
    void getFactorTypeByIdNotFound() throws Exception {
        typeId = -1L;
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
    }
}