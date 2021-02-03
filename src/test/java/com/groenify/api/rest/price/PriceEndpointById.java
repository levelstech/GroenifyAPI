package com.groenify.api.rest.price;

import com.groenify.api.loader.FactorTypeLoader;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.framework.annotation.resolver.FactorAnswerPriceInPathResolver;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.price.FactorAnswerPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerBoolean;
import static com.groenify.api.TestModelCreatorUtil.newFactorAnswerPrice;

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

        FactorTypeLoader.loadFactorTypeEnumerators(typeRepository);

        final FactorAnswerBoolean answerBoolean =
                newFactorAnswerBoolean(true, this);
        final double answerPrice = 100d;
        final double polePrice = 50d;

        final FactorAnswerPrice price = newFactorAnswerPrice(
                answerPrice, polePrice, answerBoolean, this);
        setTestPrice(price);
        setPriceId(getTestPrice().getId());

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
