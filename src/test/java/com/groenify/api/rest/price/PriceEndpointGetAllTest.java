package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.TestModelCreatorUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.price.FactorAnswerPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static com.groenify.api.TestModelCreatorUtil.newCompanyEPole;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerBoolean;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerPrice;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerText;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class PriceEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT =
            "/api/v1/company_epoles/factors/answers/prices";
    private static List<FactorAnswerPrice> testPrices;

    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorAnswerPriceRepository repository;

    public static void setTestPrices(final List<FactorAnswerPrice> var) {
        PriceEndpointGetAllTest.testPrices = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final FactorAnswerPriceEndpoint endpoint =
                new FactorAnswerPriceEndpoint(
                        new FactorAnswerPriceService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);

        final CompanyEPole companyEPoleWahid =
                newCompanyEPole(121d, this);

        final FactorAnswer answer1 =
                newFactorAnswerBoolean(true, this);
        final FactorAnswer answer3 = storeNew(
                newFactorAnswerText("text", answer1.getFactor()));

        final FactorAnswerPrice price =
                newFactorAnswerPrice(100d, answer1, companyEPoleWahid);
        final FactorAnswerPrice price2 =
                newFactorAnswerPrice(50d, answer3, companyEPoleWahid);
        setTestPrices(storeNews(List.of(price, price2)));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllFactorTypeValidateJsonKeyNames() throws Exception {

        final String boolObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerPrice.class, "1");
        final String stringObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerPrice.class, "2");
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody,
                String.format("[%s,%s]", boolObj, stringObj));
    }

    @Test
    void getAllFactorTypeValidateDatabaseValues() throws Exception {
        final FactorAnswerPrice priceWahid = testPrices.get(0);
        final FactorAnswerPrice priceThanie = testPrices.get(1);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testPrices.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", priceWahid))
                .andExpect(jsonPathIdOfModelId("$[1].id", priceThanie))

                .andExpect(jsonPath("$[0].price", is(100d)))
                .andExpect(jsonPath("$[1].price", is(50d)))

                .andExpect(jsonPathIdOfModelId(
                        "$[0].factor_answer.id", priceWahid.getFactorAnswer()))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].factor_answer.id", priceThanie.getFactorAnswer()))

                .andExpect(jsonPathIdOfModelId(
                        "$[0].company_epole.id", priceWahid.getPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].company_epole.id", priceWahid.getPole()));
    }
}
