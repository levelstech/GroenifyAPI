package com.groenify.api.rest.company;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.CompanyEPoleInPathResolver;
import com.groenify.api.repository.company.CompanyEPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.company.CompanyEPoleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class CompanyEPoleEndpointDeleteTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/company_epoles";
    private static Long poleId;
    private static CompanyEPole testPole;

    @Autowired
    private CompanyEPoleRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + poleId;
    }

    protected void setUpMock() {
        final CompanyEPoleEndpoint endpoint =
                new CompanyEPoleEndpoint(new CompanyEPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new CompanyEPoleInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final Company companyWahid = storeNew(Company.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Company-Wahid\","
                        + "\"date\":\"2020-12-28T00:43:32Z\","
                        + "\"url\":\"https://google.de\"}"));
        final EPoleBrand brandWahid = storeNew(EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}"));
        EPole ePoleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Brand-Wahid\"}");
        ePoleWahid.setBrand(brandWahid);
        ePoleWahid = storeNew(ePoleWahid);


        final CompanyEPole companyEPoleWahid =
                CompanyEPole.ofJsonObjStr("{\"base_price\":121.0}");
        companyEPoleWahid.setCompany(companyWahid);
        companyEPoleWahid.setePole(ePoleWahid);

        testPole = storeNew(companyEPoleWahid);
        poleId = testPole.getId();

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getCompanyEPolesByIdValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(repository.existsById(poleId)).isFalse();
    }

    @Test
    void getCompanyEPolesByIdValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(repository.existsById(poleId)).isFalse();
    }

    @Test
    void getCompanyEPoleByIdInvalid() throws Exception {
        poleId = -1L;

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
        poleId = testPole.getId();
    }
}