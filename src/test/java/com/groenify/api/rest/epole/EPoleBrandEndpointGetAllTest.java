package com.groenify.api.rest.epole;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.RestTestUtil;
import com.groenify.api.rest.epole.EPoleBrandEndpoint;
import com.groenify.api.service.epole.EPoleBrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class EPoleBrandEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epole_brands";

    private static List<EPoleBrand> TEST_BRANDS;

    @Autowired
    private EPoleBrandRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final EPoleBrandEndpoint endpoint =
                new EPoleBrandEndpoint(new EPoleBrandService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final EPoleBrand brandWahid = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}");
        final EPoleBrand brandThanie = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Thanie\"}");
        final EPoleBrand brandThalith = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Thalith\"}");
        TEST_BRANDS = storeNews(List.of(brandWahid, brandThanie, brandThalith));
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getAllEPoleBrandValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "[{\"id\":1, \"name\":\"Brand-Wahid\"},"
                + "{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + "{\"id\":3, \"name\":\"Brand-Thalith\"}]");
    }

    @Test
    void getAllEPoleBrandValidateDatabaseValues() throws Exception {
        final EPoleBrand brandWahid = TEST_BRANDS.get(0);
        final EPoleBrand brandThanie = TEST_BRANDS.get(1);
        final EPoleBrand brandThalith = TEST_BRANDS.get(2);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(TEST_BRANDS.size())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[0].id", brandWahid))
                .andExpect(jsonPath("$[0].name", is(brandWahid.getName())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[1].id", brandThanie))
                .andExpect(jsonPath("$[1].name", is(brandThanie.getName())))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$[2].id", brandThalith))
                .andExpect(jsonPath("$[2].name", is(brandThalith.getName())));
    }
}