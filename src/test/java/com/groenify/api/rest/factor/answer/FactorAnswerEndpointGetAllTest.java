package com.groenify.api.rest.factor.answer;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.TestModelCreatorUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.model.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.factor.answer.FactorAnswerService;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;
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
class FactorAnswerEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors/answers";
    private static List<FactorAnswer> testAnswers;
    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorAnswerRepository repository;

    public static void setTestAnswers(final List<FactorAnswer> var) {
        FactorAnswerEndpointGetAllTest.testAnswers = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final FactorAnswerEndpoint endpoint =
                new FactorAnswerEndpoint(new FactorAnswerService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);

        final FactorAnswerBoolean answer1 =
                TestModelCreatorUtil.newFactorAnswerBoolean(true, this);
        final FactorAnswerBoolean answer2 =
                TestModelCreatorUtil.newFactorAnswerBoolean(false, this);
        final FactorAnswerMultipleChoice answer3 =
                TestModelCreatorUtil.newFactorAnswerText("text", this);

        setTestAnswers(List.of(answer1, answer2, answer3));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllFactorAnswerValidateJsonKeyNames() throws Exception {

        final String boolObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerResMo.class, "1");
        final String stringObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerResMo.class, "2");

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody,
                String.format("[%s, %s, %s]", boolObj, boolObj, stringObj));
    }

    @Test
    void getAllFactorAnswerValidateDatabaseValues() throws Exception {
        final FactorAnswer answerWahid = testAnswers.get(0);
        final FactorAnswer answerThanie = testAnswers.get(1);
        final FactorAnswer answerThalith = testAnswers.get(2);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testAnswers.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", answerWahid))
                .andExpect(jsonPathIdOfModelId("$[1].id", answerThanie))
                .andExpect(jsonPathIdOfModelId("$[2].id", answerThalith))

                .andExpect(jsonPathIdOfModelId(
                        "$[0].factor.id", answerWahid.getFactor()))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].factor.id", answerThanie.getFactor()))
                .andExpect(jsonPathIdOfModelId(
                        "$[2].factor.id", answerThalith.getFactor()))

                .andExpect(jsonPathIdOfModelId(
                        "$[0].type", answerWahid.getType()))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].type", answerThanie.getType()))
                .andExpect(jsonPathIdOfModelId(
                        "$[2].type", answerThalith.getType()))

                .andExpect(jsonPath("$[0].answer", is(answerWahid.getAnswer())))
                .andExpect(jsonPath("$[1].answer",
                        is(answerThanie.getAnswer())))
                .andExpect(jsonPath(
                        "$[2].answer", is(answerThalith.getAnswer())));
    }
}
