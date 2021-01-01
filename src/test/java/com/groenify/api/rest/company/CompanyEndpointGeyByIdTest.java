package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
import com.groenify.api.framework.annotation.resolver.CompanyInPathResolver;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEndpointGeyByIdTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";
    private static Company testCompany;
    private static Long companyId;

    @Autowired
    private CompanyRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + companyId;
    }

    public static void setTestCompany(final Company var) {
        CompanyEndpointGeyByIdTest.testCompany = var;
    }

    public static void setCompanyId(final Long var) {
        CompanyEndpointGeyByIdTest.companyId = var;
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
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        setTestCompany(storeNew(companyWahid));
        setCompanyId(testCompany.getId());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getCompanyIdValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"}");
    }

    @Test
    void getCompanyByIdValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", testCompany))
                .andExpect(jsonPath("$.name", is(testCompany.getName())))
                .andExpect(jsonPath("$.date", is("2020-12-28T00:43:32Z")))
                .andExpect(jsonPath("$.url", is("https://google.de")));
    }

    @Test
    void getEPoleBrandByIdNotFound() throws Exception {
        setCompanyId(-1L);
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
    }
}
