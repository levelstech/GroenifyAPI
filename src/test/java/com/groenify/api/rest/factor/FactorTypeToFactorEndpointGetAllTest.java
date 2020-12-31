package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.RestTestUtil;
import com.groenify.api.service.factor.FactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorTypeToFactorEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static Long typeId;
    private static List<Factor> TEST_FACTORS;

    @Autowired
    private FactorRepository repository;
    @Autowired
    private FactorTypeRepository typeRepository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + typeId + "/factors";
    }

    protected void setUpMock() {
        final FactorTypeToFactorEndpoint endpoint =
                new FactorTypeToFactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(typeRepository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final FactorType typeWahid = storeNew(FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}"));
        final Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid\", "
                        + "\"question\":\"Q?\","
                        + "\"description\":\"aa\"}");
        final Factor factorThanie = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Thanie\", "
                        + "\"question\":\"Q?\","
                        + "\"description\":\"aa\"}");
        factorWahid.setType(typeWahid);
        factorThanie.setType(typeWahid);
        TEST_FACTORS = storeNews(List.of(factorWahid, factorThanie));
        typeId = typeWahid.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllEFromFactorTypeValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "[{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid\","
                + "\"question\":\"Q?\","
                + "\"description\":\"aa\"},"
                + "{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid\","
                + "\"question\":\"Q?\","
                + "\"description\":\"aa\"}]");
    }

    @Test
    void getAllFactorFromTypeValidateDatabaseValues() throws Exception {
        final Factor factorWahid = TEST_FACTORS.get(0);
        final Factor factorThanie = TEST_FACTORS.get(1);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(TEST_FACTORS.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", factorWahid))
                .andExpect(jsonPath("$[0].name", is("Factor-Wahid")))
                .andExpect(jsonPath("$[0].question", is("Q?")))
                .andExpect(jsonPathIdOfModelId("$[0].type.id", factorWahid.getType()))
                .andExpect(jsonPath("$[0].type.name", is(factorWahid.getType().getName())))
                .andExpect(jsonPath("$[0].description", is("aa")))
                .andExpect(jsonPathIdOfModelId("$[1].id", factorThanie))
                .andExpect(jsonPath("$[1].name", is("Factor-Thanie")))
                .andExpect(jsonPath("$[1].question", is("Q?")))
                .andExpect(jsonPathIdOfModelId("$[1].type.id", factorThanie.getType()))
                .andExpect(jsonPath("$[1].type.name", is(factorThanie.getType().getName())))
                .andExpect(jsonPath("$[1].description", is("aa")));
    }

    @Test
    void getAllFactorFromInvalidType() throws Exception {
        typeId = -1L;

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
    }
}