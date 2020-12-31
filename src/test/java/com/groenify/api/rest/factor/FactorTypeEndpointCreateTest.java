package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorTypeService;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";

    @Autowired
    private FactorTypeRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
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
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void postFactorTypeCreateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Type-Wahid\","
                + "\"description\":\"aaaa\"}");
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Wahid")).isTrue();
    }

    @Test
    void postFactorTypeCreateValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Thanie\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Type-Thanie")))
                .andExpect(jsonPath("$.description", is("aaaa")));

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Thanie")).isTrue();
    }

    @Test
    void postFactorTypeCreateInvalid() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"peter\":\"Type-Thalith\"}"))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Thalith")).isFalse();
    }
}
