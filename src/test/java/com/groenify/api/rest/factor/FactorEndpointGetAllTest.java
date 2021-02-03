package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.repository.factor.FactorRepository;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.factor.FactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.TestModelCreatorUtil.newFactor;
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
    private static List<Factor> testFactors;

    @Autowired
    private FactorRepository repository;
    @Autowired
    private FactorTypeRepository typeRepository;

    public static void setTestFactors(final List<Factor> var) {
        FactorEndpointGetAllTest.testFactors = var;
    }

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

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);

        final Factor factorWahid =
                newFactor(this, FactorTypeEnum.BOOLEAN_QUESTION);
        final Factor factorThanie =
                newFactor(this, FactorTypeEnum.BOOLEAN_QUESTION);
        setTestFactors(List.of(factorWahid, factorThanie));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllFactorTypeValidateJsonKeyNames() throws Exception {
        final String resObj = TestRestObjectGetterUtil.
                getJsonResponseObject(Factor.class);

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, String.format("[%s,%s]", resObj, resObj));
    }

    @Test
    void getAllFactorTypeValidateDatabaseValues() throws Exception {
        final Factor factorWahid = testFactors.get(0);
        final Factor factorThanie = testFactors.get(1);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testFactors.size())))
                .andExpect(jsonPath("$[0].name", is(factorWahid.getName())))
                .andExpect(jsonPath("$[1].name", is(factorThanie.getName())))

                .andExpect(jsonPathIdOfModelId("$[0].id", factorWahid))
                .andExpect(jsonPathIdOfModelId("$[1].id", factorThanie))


                .andExpect(jsonPath("$[0].question",
                        is(factorWahid.getQuestion())))
                .andExpect(jsonPath("$[1].question",
                        is(factorThanie.getQuestion())))

                .andExpect(jsonPath("$[0].description",
                        is(factorWahid.getDescription())))
                .andExpect(jsonPath("$[1].description",
                        is(factorThanie.getDescription())));
    }
}
