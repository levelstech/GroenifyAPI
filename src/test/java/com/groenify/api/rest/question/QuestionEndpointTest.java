package com.groenify.api.rest.question;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.TestModelCreatorUtil;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.repository.factor.FactorRepository;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.service.factor.FactorService;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.question.__model.QuestionResMo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.TestModelCreatorUtil.newFactor;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerBoolean;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerDoubleNumber;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerNumber;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerText;
import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;

import static com.groenify.api.rest.TestRestObjectGetterUtil.getJsonResponseObject;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class QuestionEndpointTest extends EndpointTest {

    private static final String ENDPOINT =
            "/api/v1/application/questions";
    private static List<Factor> testFactors;

    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorRepository repository;

    public static void setTestFactors(final List<Factor> var) {
        QuestionEndpointTest.testFactors = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final QuestionEndpoint endpoint =
                new QuestionEndpoint(
                        new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);


        final Factor factor1 = newFactorAnswerBoolean(true, this).getFactor();
        storeNew(newFactorAnswerBoolean(false, factor1));
        final Factor factor2 = newFactorAnswerText("Answer_1", this).getFactor();
        storeNew(newFactorAnswerText("Answer_2", factor2));
        final Factor factor3 = newFactorAnswerNumber(1d, 10d, this).getFactor();
        final Factor factor4 = newFactorAnswerDoubleNumber(1d, 10d, 3d, 5d, this).getFactor();
        setTestFactors(List.of(factor1, factor2, factor3, factor4));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllFactorTypeValidateJsonKeyNames() throws Exception {

        final String boolObj = getJsonResponseObject(QuestionResMo.class, "1");
        final String stringObj = getJsonResponseObject(QuestionResMo.class, "2");
        final String numberObj = getJsonResponseObject(QuestionResMo.class, "3");
        final String dNumberObj = getJsonResponseObject(QuestionResMo.class, "4");

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody,
                String.format("[%s,%s,%s,%s]",
                        boolObj, stringObj, numberObj, dNumberObj));
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
                .andExpect(jsonPath("$[0].question",
                        is(factorWahid.getQuestion())))
                .andExpect(jsonPath("$[1].question",
                        is(factorThanie.getQuestion())))
                .andExpect(jsonPath("$[0].required",
                        is(factorWahid.getRequired())))
                .andExpect(jsonPath("$[1].required",
                        is(factorThanie.getRequired())));
    }
}