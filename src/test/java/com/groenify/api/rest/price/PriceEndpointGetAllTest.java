package com.groenify.api.rest.price;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.price.FactorAnswerPrice;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.price.FactorAnswerPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static com.groenify.api.util.TestModelCreatorUtil.newCompany;
import static com.groenify.api.util.TestModelCreatorUtil.newCompanyEPole;
import static com.groenify.api.util.TestModelCreatorUtil.newEPole;
import static com.groenify.api.util.TestModelCreatorUtil.newEPoleBrand;
import static com.groenify.api.util.TestModelCreatorUtil.newFactor;
import static com.groenify.api.util.TestModelCreatorUtil.newFactorAnswerBoolean;
import static com.groenify.api.util.TestModelCreatorUtil.newFactorAnswerPrice;
import static com.groenify.api.util.TestModelCreatorUtil.newFactorText;
import static com.groenify.api.util.TestModelCreatorUtil.newFactorType;
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

        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final Company companyWahid = storeNew(newCompany());
        final EPoleBrand brandWahid = storeNew(newEPoleBrand());
        final EPole ePoleWahid = storeNew(newEPole(brandWahid));
        final CompanyEPole companyEPoleWahid = storeNew(
                newCompanyEPole(121d, companyWahid, ePoleWahid));
        final FactorType type = storeNew(newFactorType());
        final Factor factorWahid = storeNew(newFactor(type));

        final FactorAnswer answer1 = storeNew(
                newFactorAnswerBoolean(true, factorWahid));

        final FactorAnswer answer3 = storeNew(
                newFactorText("text", factorWahid));

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

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "["
                + "{\"id\":3,\"price\":100.0,"
                + "\"factor_answer\":{\"id\":3,"
                + "\"factor\":{\"id\":2,"
                + "\"type\":{\"id\":10,\"name\":\"Type-Wahid\","
                + "\"description\":null},"
                + "\"name\":\"Factor-Wahid1\",\"question\":\"Q11?\","
                + "\"description\":\"dd\"},\"type\":6,\"answer\":true},"
                + "\"company_epole\":{\"id\":2,\"company\":"
                + "{\"id\":2,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":121.0,\"epole\":{\"id\":2,\"brand\":"
                + "{\"id\":2,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}},"
                + "{\"id\":4,\"price\":50.0,\"factor_answer\":"
                + "{\"id\":4,\"factor\":{\"id\":2,\"type\":"
                + "{\"id\":10,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid1\",\"question\":\"Q11?\","
                + "\"description\":\"dd\"},\"type\":7,\"answer\":\"text\"},"
                + "\"company_epole\":{\"id\":2,\"company\":"
                + "{\"id\":2,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":121.0,\"epole\":"
                + "{\"id\":2,\"brand\":{\"id\":2,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}}]");
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
