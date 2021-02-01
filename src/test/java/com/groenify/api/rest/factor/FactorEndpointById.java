package com.groenify.api.rest.factor;

import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.framework.annotation.resolver.FactorInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.TestModelCreatorUtil.newFactor;

@DataJpaTest()
@EnableAutoConfiguration
abstract class FactorEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private static Long factorId;
    private static Factor testFactor;

    @Autowired
    private FactorRepository repository;
    @Autowired
    private FactorTypeRepository typeRepository;

    protected static void setFactorId(final Long var) {
        FactorEndpointById.factorId = var;
    }

    protected static Factor getTestFactor() {
        return testFactor;
    }

    private static void setTestFactor(final Factor var) {
        FactorEndpointById.testFactor = var;
    }

    protected final FactorRepository getRepository() {
        return repository;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + factorId;
    }

    protected final void setUpMock() {
        final FactorEndpoint endpoint =
                new FactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected final void setUpData() {

        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final Factor factorWahid =
                newFactor(this, FactorTypeEnum.BOOLEAN_QUESTION);
        setTestFactor(factorWahid);
        setFactorId(getTestFactor().getId());

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
