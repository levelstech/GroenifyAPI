package com.groenify.api.rest.epole;

import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.EPoleInPathResolver;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.epole.EPoleService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

public class EPoleEndpointById extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epoles";
    private static Long poleId;
    private static EPole testPole;

    @Autowired
    private EPoleRepository repository;

    protected static void setPoleId(final Long var) {
        poleId = var;
    }

    protected static EPole getTestPole() {
        return testPole;
    }

    @Override
    protected final String getEndpoint() {
        return ENDPOINT + "/" + poleId;
    }

    protected final EPoleRepository getRepository() {
        return repository;
    }

    protected final void setUpMock() {
        final EPoleEndpoint endpoint =
                new EPoleEndpoint(new EPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new EPoleInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected final void setUpData() {

        final EPoleBrand brandWahid = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}");
        final EPoleBrand brand = storeNew(brandWahid);
        final EPole poleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Pole-Wahid\", \"description\":\"aa\"}");
        poleWahid.setBrand(brand);
        testPole = storeNew(poleWahid);
        poleId = testPole.getId();

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

}
