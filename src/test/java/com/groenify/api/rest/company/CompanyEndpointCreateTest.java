package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.framework.annotation.resolver.CompanyInPathResolver;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.company.CompanyEndpoint;
import com.groenify.api.service.company.CompanyService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";

    @Autowired
    private CompanyRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final CompanyEndpoint endpoint =
                new CompanyEndpoint(new CompanyService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new CompanyInPathResolver(repository));
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
    void postCompanyCreateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Company-Wahid\","
                                + "\"date\":\"2020-12-28T00:43:32Z\","
                                + "\"url\":\"https://google.de\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1,"
                + "\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"}");

        Assertions.assertThat(
                repository.existsByNameIgnoreCase("Company-Wahid")).isTrue();
    }

    @Test
    void postCompanyCreateValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Company-Thanie\","
                                + "\"date\":\"2020-12-28T00:43:32Z\","
                                + "\"url\":\"https://google.de\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Company-Thanie")))
                .andExpect(jsonPath("$.date", is("2020-12-28T00:43:32Z")))
                .andExpect(jsonPath("$.url", is("https://google.de")));

        Assertions.assertThat(
                repository.existsByNameIgnoreCase("Company-Thanie")).isTrue();
    }

    @Test
    void postCompanyCreateInvalid() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"peter\":\"Brand-Thalith\","
                                + "\"date\":\"2020-12-28T00:43:32Z\","
                                + "\"url\":\"https://google.de\"}"))
                .andExpect(status().isBadRequest());
        Assertions.assertThat(
                repository.existsByNameIgnoreCase("Brand-Thalith")).isFalse();
    }
}
