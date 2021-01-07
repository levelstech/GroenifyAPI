package com.groenify.api.rest.factor.answer;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.answer.FactorAnswerService;
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

    private static final String ENDPOINT = "/api/v1/factors/answers";
    private static List<FactorAnswer> testAnswers;
    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorAnswerRepository repository;

    public static void setTestAnswers(final List<FactorAnswer> var) {
        FactorEndpointGetAllTest.testAnswers = var;
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

        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        final FactorType type = storeNew(typeWahid);
        Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid1\","
                        + "\"question\":\"Q11?\","
                        + "\"description\":\"dd\"}");
        factorWahid.setType(type);
        factorWahid = storeNew(factorWahid);
        final FactorAnswerBoolean answerBoolean =
                FactorAnswerBoolean.ofJsonObjStr(
                        "{\"id\":1, \"answer_boolean\":true}");
        answerBoolean.setFactor(factorWahid);
        final FactorAnswerBoolean answerBoolean2 =
                FactorAnswerBoolean.ofJsonObjStr(
                        "{\"id\":1, \"answer_boolean\":false}");
        answerBoolean2.setFactor(factorWahid);
        final FactorAnswerMultipleChoice answerMulti =
                FactorAnswerMultipleChoice.ofJsonObjStr(
                        "{\"id\":1, \"answer_multiple\":\"text\"}");
        answerMulti.setFactor(factorWahid);

        final FactorAnswer answer1 = repository.save(answerBoolean);
        final FactorAnswer answer2 = repository.save(answerBoolean2);
        final FactorAnswer answer3 = repository.save(answerMulti);
        setTestAnswers(List.of(answer1, answer2, answer3));
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

        JsonTestUtil.test(resBody, "[{\"id\":3,"
                + "\"factor\":"
                + "{\"id\":2,"
                + "\"type\":{\"id\":10,\"name\":\"Type-Wahid\","
                + "\"description\":null},"
                + "\"name\":\"Factor-Wahid1\","
                + "\"question\":\"Q11?\","
                + "\"description\":\"dd\"},"
                + "\"type\":6,"
                + "\"answer\":true},"
                + "{\"id\":4,"
                + "\"factor\":{\"id\":2,\"type\":{\"id\":10,"
                + "\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid1\",\"question\":\"Q11?\","
                + "\"description\":\"dd\"},\"type\":6,\"answer\":false},"
                + "{\"id\":5,\"factor\":{\"id\":2,\"type\":"
                + "{\"id\":10,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid1\",\"question\":\"Q11?\","
                + "\"description\":\"dd\"},\"type\":7,\"answer\":\"text\"}]");
    }

    @Test
    void getAllFactorTypeValidateDatabaseValues() throws Exception {
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
                        "$[1].factor.id", answerThalith.getFactor()))

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
