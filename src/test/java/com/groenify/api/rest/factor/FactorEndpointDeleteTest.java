package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorEndpointDeleteTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private static Long factorId;
    private static Factor testFactor;

    @Autowired
    private FactorRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + factorId;
    }

    protected void setUpMock() {
        final FactorEndpoint endpoint =
                new FactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

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

    @Test
    void deleteEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(repository.existsByNameIgnoreCase("Factor-Wahid")).isFalse();
    }

    @Test
    void deleteFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));
        Assertions.assertThat(repository.existsByNameIgnoreCase("Factor-Wahid")).isFalse();
    }

    @Test
    void deleteFactorInvalid() throws Exception {
        factorId = -1L;

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());
        Assertions.assertThat(repository.existsByNameIgnoreCase("Factor-Wahid")).isTrue();
        factorId = testFactor.getId();
    }
}