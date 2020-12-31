package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorTypeService;
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

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointDeleteTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static Long typeId;

    @Autowired
    private FactorTypeRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + typeId;
    }

    protected void setUpMock() {
        final FactorTypeEndpoint endpoint =
                new FactorTypeEndpoint(new FactorTypeService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        final FactorType testType = storeNew(typeWahid);
        typeId = testType.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void deleteFactorTypeDeleteValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(repository.existsByNameIgnoreCase("Type-Wahid")).isFalse();
    }

    @Test
    void deleteFactorTypeDeleteValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(repository.existsByNameIgnoreCase("Type-Wahid")).isFalse();
    }

    @Test
    void deleteFactorTypeDeleteInvalid() throws Exception {
        typeId = -1L;
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase("Type-Wahid")).isTrue();
    }
}