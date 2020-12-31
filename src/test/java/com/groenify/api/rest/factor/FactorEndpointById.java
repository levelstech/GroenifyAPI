package com.groenify.api.rest.factor;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@DataJpaTest()
@EnableAutoConfiguration
class FactorEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private static Long factorId;
    private static Factor testFactor;

    @Autowired
    private FactorRepository repository;

    protected static void setFactorId(final Long var) {
        FactorEndpointById.factorId = var;
    }

    protected static Factor getTestFactor() {
        return testFactor;
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

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        final FactorType type = storeNew(typeWahid);
        final Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid\", "
                        + "\"question\":\"Q?\","
                        + "\"description\":\"aa\"}");
        factorWahid.setType(type);
        testFactor = storeNew(factorWahid);
        factorId = testFactor.getId();

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
