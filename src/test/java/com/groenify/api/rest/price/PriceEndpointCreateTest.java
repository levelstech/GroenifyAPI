package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.framework.annotation.resolver.CompanyEPoleInPathResolver;
import com.groenify.api.framework.annotation.resolver.FactorAnswerInPathResolver;
import com.groenify.api.database.repository.company.CompanyEPoleRepository;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.price.FactorAnswerPriceService;
import com.groenify.api.rest.price.__model.FactorAnswerPriceResMo;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.TestModelCreatorUtil.newCompanyEPole;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerBoolean;
import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class PriceEndpointCreateTest extends EndpointTest {

    private static final String BASE_OF_PATH = "/api/v1/company_epoles/";
    private static final String END_OF_PATH = "/factors/answers/";
    private static long companyEPoleId;
    private static long answerId;

    private CompanyEPole ePole;
    private FactorAnswer factorAnswer;

    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorAnswerPriceRepository repository;
    @Autowired
    private CompanyEPoleRepository companyEPoleRepository;
    @Autowired
    private FactorAnswerRepository factorAnswerRepository;


    public static void setCompanyEPoleId(final long var) {
        PriceEndpointCreateTest.companyEPoleId = var;
    }

    public static void setAnswerId(final long var) {
        PriceEndpointCreateTest.answerId = var;
    }

    @Override
    protected String getEndpoint() {
        return BASE_OF_PATH + companyEPoleId + END_OF_PATH + answerId
                + "/prices";
    }

    protected void setUpMock() {
        final FactorAnswerPriceEndpoint endpoint =
                new FactorAnswerPriceEndpoint(
                        new FactorAnswerPriceService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new CompanyEPoleInPathResolver(companyEPoleRepository),
                new FactorAnswerInPathResolver(factorAnswerRepository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);
        ePole = newCompanyEPole(500d, this);
        factorAnswer = newFactorAnswerBoolean(true, this);
        setCompanyEPoleId(ePole.getId());
        setAnswerId(factorAnswer.getId());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void postFactorTypeCreateValidateJsonKeyNames() throws Exception {

        final String resObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerPriceResMo.class, "1");

        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":100}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, resObj);
        Assertions.assertThat(repository.findAllByPoleAndFactorAnswer(
                ePole, factorAnswer)).isPresent();
    }

    @Test
    void postFactorTypeCreateValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPathIdOfModelId(
                        "$.factor_answer.id", factorAnswer))
                .andExpect(jsonPathIdOfModelId("$.company_epole.id", ePole))
                .andExpect(jsonPath("$.price", is(100d)));

        Assertions.assertThat(repository.findAllByPoleAndFactorAnswer(
                ePole, factorAnswer)).isPresent();
    }

    @Test
    void postFactorTypeCreateInvalid() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price_\":100}"))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(repository.findAllByPoleAndFactorAnswer(
                ePole, factorAnswer)).isEmpty();
    }
}
