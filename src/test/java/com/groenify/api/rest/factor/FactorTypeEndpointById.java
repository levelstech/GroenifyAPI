package com.groenify.api.rest.factor;

import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static FactorType testType;
    private static Long typeId;

    @Autowired
    private FactorTypeRepository repository;

    protected static FactorType getTestType() {
        return testType;
    }

    protected static void setTypeId(final Long var) {
        typeId = var;
    }

    public static void setTestType(final FactorType var) {
        FactorTypeEndpointById.testType = var;
    }

    protected final FactorTypeRepository getRepository() {
        return repository;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + typeId;
    }

    protected final void setUpMock() {
        final FactorTypeEndpoint endpoint =
                new FactorTypeEndpoint(new FactorTypeService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected final void setUpData() {
        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\","
                        + "\"description\":\"aaaa\"}");
        setTestType(storeNew(typeWahid));
        setTypeId(testType.getId());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }
}
