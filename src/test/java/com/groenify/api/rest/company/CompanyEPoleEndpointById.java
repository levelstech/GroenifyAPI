package com.groenify.api.rest.company;

import com.groenify.api.database.company.Company;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.CompanyEPoleInPathResolver;
import com.groenify.api.repository.company.CompanyEPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.company.CompanyEPoleService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
abstract class CompanyEPoleEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/company_epoles";
    private static Long poleId;
    private static CompanyEPole testPole;

    @Autowired
    private CompanyEPoleRepository repository;

    public static Long getPoleId() {
        return poleId;
    }

    public static void setPoleId(final Long var) {
        CompanyEPoleEndpointById.poleId = var;
    }

    public static CompanyEPole getTestPole() {
        return testPole;
    }

    private static void setTestPole(final CompanyEPole var) {
        CompanyEPoleEndpointById.testPole = var;
    }

    public CompanyEPoleRepository getRepository() {
        return repository;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + poleId;
    }

    protected final void setUpMock() {
        final CompanyEPoleEndpoint endpoint =
                new CompanyEPoleEndpoint(new CompanyEPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new CompanyEPoleInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected final void setUpData() {
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

        setTestPole(storeNew(companyEPoleWahid));
        setPoleId(testPole.getId());

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

}
