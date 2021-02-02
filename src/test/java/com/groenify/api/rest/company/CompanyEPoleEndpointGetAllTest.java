package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.repository.company.CompanyEPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.company.CompanyEPoleService;
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
class CompanyEPoleEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/company_epoles";

    private static List<CompanyEPole> testPoles;

    @Autowired
    private CompanyEPoleRepository repository;

    public static void setTestPoles(final List<CompanyEPole> var) {
        CompanyEPoleEndpointGetAllTest.testPoles = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final CompanyEPoleEndpoint endpoint =
                new CompanyEPoleEndpoint(new CompanyEPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final Company companyWahid = storeNew(Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}"));
        final Company companyThanie = storeNew(Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Thanie\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}"));

        final EPoleBrand brandWahid = storeNew(EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}"));
        EPole ePoleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Brand-Wahid\"}");
        ePoleWahid.setBrand(brandWahid);
        ePoleWahid = storeNew(ePoleWahid);

        EPole ePoleThanie = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Brand-Wahid\"}");
        ePoleThanie.setBrand(brandWahid);
        ePoleThanie = storeNew(ePoleThanie);


        final CompanyEPole companyEPoleWahid =
                CompanyEPole.ofJsonObjStr("{\"base_price\":121.0}");
        companyEPoleWahid.setCompany(companyWahid);
        companyEPoleWahid.setEPole(ePoleWahid);
        final CompanyEPole companyEPoleThanie =
                CompanyEPole.ofJsonObjStr("{\"base_price\":122.0}");
        companyEPoleThanie.setCompany(companyWahid);
        companyEPoleThanie.setEPole(ePoleThanie);
        final CompanyEPole companyEPoleThalith =
                CompanyEPole.ofJsonObjStr("{\"base_price\":222.0}");
        companyEPoleThalith.setCompany(companyThanie);
        companyEPoleThalith.setEPole(ePoleThanie);

        setTestPoles(storeNews(List.of(companyEPoleWahid,
                companyEPoleThanie, companyEPoleThalith)));

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllCompanyEPolesValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "[{\"id\":7,\"company\":"
                + "{\"id\":5,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":121.0,\"epole\":"
                + "{\"id\":5,\"brand\":{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}},"
                + "{\"id\":8,\"company\":"
                + "{\"id\":5,\"name\":\"Company-Wahid\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},\"base_price\":122.0,"
                + "\"epole\":{\"id\":6,\"brand\":"
                + "{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}},"
                + "{\"id\":9,\"company\":{\"id\":6,\"name\":\"Company-Thanie\","
                + "\"date\":\"2020-12-28T00:43:32Z\","
                + "\"url\":\"https://google.de\"},"
                + "\"base_price\":222.0,\"epole\":{\"id\":6,"
                + "\"brand\":{\"id\":3,\"name\":\"Brand-Wahid\"},"
                + "\"type\":\"Brand-Wahid\",\"description\":null}}]");
    }

    @Test
    void getAllCompanyEPolesValidateDatabaseValues() throws Exception {
        final CompanyEPole ePole1 = testPoles.get(0);
        final CompanyEPole ePole2 = testPoles.get(1);
        final CompanyEPole ePole3 = testPoles.get(2);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testPoles.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", ePole1))
                .andExpect(jsonPathIdOfModelId(
                        "$[0].epole.id", ePole1.getEPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$[0].company.id", ePole1.getCompany()))
                .andExpect(jsonPath(
                        "$[0].base_price", is(ePole1.getBasePrice())))
                .andExpect(jsonPathIdOfModelId("$[1].id", ePole2))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].epole.id", ePole2.getEPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].company.id", ePole2.getCompany()))
                .andExpect(jsonPath(
                        "$[1].base_price", is(ePole2.getBasePrice())))
                .andExpect(jsonPathIdOfModelId("$[2].id", ePole3))
                .andExpect(jsonPathIdOfModelId(
                        "$[2].epole.id", ePole3.getEPole()))
                .andExpect(jsonPathIdOfModelId(
                        "$[2].company.id", ePole3.getCompany()))
                .andExpect(jsonPath(
                        "$[2].base_price", is(ePole3.getBasePrice())));
    }
}
