package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.rest.EndpointTest;
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

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private FactorType type;
    private static List<Factor> testFactors;

    @Autowired
    private FactorRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final FactorEndpoint endpoint =
                new FactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        type = storeNew(typeWahid);
        final FactorType typeThanie = storeNew(FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Thanie\"}"));
        final Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid1\","
                        + "\"question\":\"Q11?\","
                        + "\"description\":\"dd\"}");
        final Factor factorThanie = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Thanie2\","
                        + "\"question\":\"Q22?\","
                        + "\"description\":\"cc\"}");
        factorWahid.setType(type);
        factorThanie.setType(typeThanie);
        testFactors = storeNews(List.of(factorWahid, factorThanie));
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
    void getAllFactorTypeValidateDatabaseValues() throws Exception {
        final Factor factorWahid = testFactors.get(0);
        final Factor factorThanie = testFactors.get(1);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testFactors.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", factorWahid))
                .andExpect(jsonPathIdOfModelId("$[1].id", factorThanie))

                .andExpect(jsonPath("$[0].name", is("Factor-Wahid1")))
                .andExpect(jsonPath("$[1].name", is("Factor-Thanie2")))

                .andExpect(jsonPath("$[0].question", is("Q11?")))
                .andExpect(jsonPath("$[1].question", is("Q22?")))

                .andExpect(jsonPath("$[0].description", is("dd")))
                .andExpect(jsonPath("$[1].description", is("cc")));
    }
}
