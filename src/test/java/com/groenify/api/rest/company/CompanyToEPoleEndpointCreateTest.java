package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.CompanyInPathResolver;
import com.groenify.api.framework.annotation.resolver.EPoleInPathResolver;
import com.groenify.api.repository.company.CompanyEPoleRepository;
import com.groenify.api.repository.company.CompanyRepository;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.company.CompanyEPoleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyToEPoleEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/companies";
    private static Long companyId;
    private static Company testCompany;
    private static Long ePoleId;
    private static EPole testEPole;

    @Autowired
    private CompanyEPoleRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EPoleRepository ePoleRepository;

    public static void setCompanyId(final Long var) {
        CompanyToEPoleEndpointCreateTest.companyId = var;
    }

    public static void setTestCompany(final Company var) {
        CompanyToEPoleEndpointCreateTest.testCompany = var;
    }

    public static void setEPoleId(final Long var) {
        CompanyToEPoleEndpointCreateTest.ePoleId = var;
    }

    public static void setTestEPole(final EPole var) {
        CompanyToEPoleEndpointCreateTest.testEPole = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + companyId + "/epoles/" + ePoleId;
    }

    protected void setUpMock() {
        final CompanyToEPoleEndpoint endpoint =
                new CompanyToEPoleEndpoint(new CompanyEPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new CompanyInPathResolver(companyRepository),
                new EPoleInPathResolver(ePoleRepository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final Company companyWahid = Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}");
        setTestCompany(storeNew(companyWahid));
        final EPoleBrand brandWahid = storeNew(EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}"));
        final EPole ePoleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"EPole-Wahid\"}");
        ePoleWahid.setBrand(brandWahid);
        setTestEPole(storeNew(ePoleWahid));

        setCompanyId(testCompany.getId());
        setEPoleId(testEPole.getId());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void postCompanyEPoleCreateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonTestUtil.test(resBody, "{\"id\":7,\"company\":"
                + "{\"id\":5,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":50.51,\"epole\":"
                + "{\"id\":5,\"brand\":{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}");
    }

    @Test
    void postCompanyEPoleCreateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPathIdOfModelId("$.epole.id", testEPole))
                .andExpect(jsonPathIdOfModelId("$.company.id", testCompany))
                .andExpect(jsonPath("$.base_price", is(50.51d)));

        final List<CompanyEPole> ePoleList =
                repository.findAllByCompany(testCompany);
        Assertions.assertThat(ePoleList).hasSize(1);
        final CompanyEPole testPole = ePoleList.get(0);

        Assertions.assertThat(testPole.getBasePrice()).isEqualTo(50.51d);
    }

    @Test
    void postCompanyEPoleCreateInvalidCompany() throws Exception {
        setCompanyId(-1L);
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isNotFound());

        final List<CompanyEPole> ePoleList =
                repository.findAllByCompany(testCompany);
        Assertions.assertThat(ePoleList).hasSize(0);
        setCompanyId(testCompany.getId());
    }

    @Test
    void postCompanyEPoleCreateInvalidEPole() throws Exception {
        setEPoleId(-1L);
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"base_price\":50.51}"))
                .andExpect(status().isNotFound());

        final List<CompanyEPole> ePoleList =
                repository.findAllByCompany(testCompany);
        Assertions.assertThat(ePoleList).hasSize(0);
        setEPoleId(testEPole.getId());
    }
}
