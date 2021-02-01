package com.groenify.api.rest.factor.answer;

import com.groenify.api.TestModelCreatorUtil;
import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerBoolean;
import com.groenify.api.framework.annotation.resolver.FactorAnswerInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.answer.FactorAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@DataJpaTest()
@EnableAutoConfiguration
class FactorAnswerEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors/answers";
    private static Long answerId;
    private static FactorAnswer testAnswer;

    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorAnswerRepository repository;

    protected static void setAnswerId(final Long var) {
        FactorAnswerEndpointById.answerId = var;
    }

    protected static FactorAnswer getTestAnswer() {
        return testAnswer;
    }

    private static void setTestAnswer(final FactorAnswer var) {
        FactorAnswerEndpointById.testAnswer = var;
    }

    protected final FactorAnswerRepository getRepository() {
        return repository;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + answerId;
    }

    protected final void setUpMock() {
        final FactorAnswerEndpoint endpoint =
                new FactorAnswerEndpoint(new FactorAnswerService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorAnswerInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final FactorAnswerBoolean answerBoolean =
                TestModelCreatorUtil.newFactorAnswerBoolean(true, this);

        setTestAnswer(answerBoolean);
        setAnswerId(testAnswer.getId());
    }
    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
