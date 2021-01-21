package com.groenify.api.rest.price;

import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.factor.answer.FactorAnswerMultipleChoice;
import com.groenify.api.database.price.FactorAnswerPrice;
import com.groenify.api.framework.annotation.resolver.FactorAnswerPriceInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.price.FactorAnswerPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@DataJpaTest()
@EnableAutoConfiguration
abstract class PriceEndpointById extends EndpointTest {

    private static final String ENDPOINT =
            "/api/v1/company_epoles/factors/answers/prices";
    private static Long priceId;
    private static FactorAnswerPrice testPrice;

    @Autowired
    private FactorTypeRepository typeRepository;

    @Autowired
    private FactorAnswerPriceRepository repository;

    public static Long getPriceId() {
        return priceId;
    }

    protected static void setPriceId(final Long var) {
        PriceEndpointById.priceId = var;
    }

    protected static FactorAnswerPrice getTestPrice() {
        return testPrice;
    }

    private static void setTestPrice(final FactorAnswerPrice var) {
        PriceEndpointById.testPrice = var;
    }

    protected final FactorAnswerPriceRepository getRepository() {
        return repository;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + priceId;
    }

    protected final void setUpMock() {
        final FactorAnswerPriceEndpoint endpoint =
                new FactorAnswerPriceEndpoint(
                        new FactorAnswerPriceService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorAnswerPriceInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected final void setUpData() {


        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final Company companyWahid = storeNew(Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}"));

        final EPoleBrand brandWahid = storeNew(EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}"));
        EPole ePoleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Brand-Wahid\"}");
        ePoleWahid.setBrand(brandWahid);
        ePoleWahid = storeNew(ePoleWahid);


        CompanyEPole companyEPoleWahid =
                CompanyEPole.ofJsonObjStr("{\"base_price\":121.0}");
        companyEPoleWahid.setCompany(companyWahid);
        companyEPoleWahid.setePole(ePoleWahid);

        companyEPoleWahid = storeNew(companyEPoleWahid);

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
        final FactorAnswerMultipleChoice answerMulti =
                FactorAnswerMultipleChoice.ofJsonObjStr(
                        "{\"id\":1, \"answer_multiple\":\"text\"}");
        answerMulti.setFactor(factorWahid);

        final FactorAnswer answer1 = storeNew(answerBoolean);
        final FactorAnswerPrice price =
                FactorAnswerPrice.ofJsonObjSr("{\"price\":100}");
        price.setFactorAnswer(answer1);
        price.setPole(companyEPoleWahid);
        setTestPrice(storeNew(price));
        setPriceId(getTestPrice().getId());

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
