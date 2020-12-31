package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
import com.groenify.api.framework.annotation.resolver.CompanyInPathResolver;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.rest.EndpointTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEndpointUpdateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";
    private static Long companyId;
    private Company testCompany;

    @Autowired
    private CompanyRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + companyId;
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
        final Company companyWahid = Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:12Z\","
                        + "\"url\":\"https://google.com\"}");
        testCompany = storeNew(companyWahid);
        companyId = testCompany.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void putCompanyUpdateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Company-Wahid(1)\","
                                + "\"date\":\"2020-12-28T00:43:32Z\","
                                + "\"url\":\"https://google.de\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonTestUtil.test(resBody, "{\"id\":1,"
                + "\"name\":\"Company-Wahid(1)\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"}");
    }

    @Test
    void putCompanyUpdateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Company-Thanie(1)\","
                                + "\"date\":\"2020-12-28T00:43:22Z\","
                                + "\"url\":\"https://google.nl\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Company-Thanie(1)")))
                .andExpect(jsonPath("$.date", is("2020-12-28T00:43:22Z")))
                .andExpect(jsonPath("$.url", is("https://google.nl")));


        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Company-Thanie")).isFalse();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Company-Thanie(1)")).isTrue();

        Assertions.assertThat(testCompany.getName())
                .isEqualTo("Company-Thanie(1)");
        Assertions.assertThat(testCompany.getDateString())
                .isEqualTo("2020-12-28T00:43:22Z");
        Assertions.assertThat(testCompany.getUrl())
                .isEqualTo("https://google.nl");
    }

    @Test
    void putCompanyUpdateInvalid() throws Exception {
        companyId = -1L;
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Company-Thalith(1)\","
                                + "\"date\":\"2020-12-28T00:43:22Z\","
                                + "\"url\":\"https://google.sa\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Company-Wahid")).isTrue();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Company-Thalith(1)")).isFalse();
        companyId = testCompany.getId();
    }

}
