package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
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

import java.util.List;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";

    private static List<Company> TEST_BRANDS;

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

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final Company companyWahid = Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        final Company companyThanie = Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Thanie\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        final Company companyThalith = Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Thalith\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        TEST_BRANDS = storeNews(List.of(companyWahid, companyThanie, companyThalith));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllCompanyValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "[{\"id\":1, \"name\":\"Brand-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https:///google.de\"},"
                + "{\"id\":2, \"name\":\"Brand-Thanie\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "{\"id\":3, \"name\":\"Brand-Thalith\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"}]");
    }

    @Test
    void getAllCompanyValidateDatabaseValues() throws Exception {
        final Company companyWahid = TEST_BRANDS.get(0);
        final Company companyThanie = TEST_BRANDS.get(1);
        final Company companyThalith = TEST_BRANDS.get(2);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(TEST_BRANDS.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", companyWahid))
                .andExpect(jsonPath("$[0].name", is(companyWahid.getName())))
                .andExpect(jsonPath("$[0].date", is("2020-12-28T00:43:32Z")))
                .andExpect(jsonPath("$[0].url", is("https://google.de")))
                .andExpect(jsonPathIdOfModelId("$[1].id", companyThanie))
                .andExpect(jsonPath("$[1].name", is(companyThanie.getName())))
                .andExpect(jsonPath("$[1].date", is("2020-12-28T00:43:32Z")))
                .andExpect(jsonPath("$[1].url", is("https://google.de")))
                .andExpect(jsonPathIdOfModelId("$[2].id", companyThalith))
                .andExpect(jsonPath("$[2].name", is(companyThalith.getName())))
                .andExpect(jsonPath("$[2].date", is("2020-12-28T00:43:32Z")))
                .andExpect(jsonPath("$[2].url", is("https://google.de")));
    }
}