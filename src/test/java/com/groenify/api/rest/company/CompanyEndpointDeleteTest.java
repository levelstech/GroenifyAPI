package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
import com.groenify.api.framework.annotation.resolver.CompanyInPathResolver;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.company.CompanyEndpoint;
import com.groenify.api.service.company.CompanyService;
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
class CompanyEndpointDeleteTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";
    private static Long companyId;

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
                "{\"id\":1,\"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        final Company testBrand = storeNew(companyWahid);
        companyId = testBrand.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void deleteEPoleBrandDeleteValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(repository.existsByNameIgnoreCase("Company-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleBrandDeleteValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(repository.existsByNameIgnoreCase("Company-Wahid")).isFalse();
    }

    @Test
    void deleteEPoleBrandDeleteInvalid() throws Exception {
        companyId = -1L;
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase("Company-Wahid")).isTrue();
    }
}