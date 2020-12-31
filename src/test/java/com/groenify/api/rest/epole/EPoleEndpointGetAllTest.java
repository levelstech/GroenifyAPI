package com.groenify.api.rest.epole;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.epole.EPoleService;
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
class EPoleEndpointGetAllTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epoles";
    private EPoleBrand brand;
    private static List<EPole> testPoles;

    @Autowired
    private EPoleRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    protected void setUpMock() {
        final EPoleEndpoint endpoint =
                new EPoleEndpoint(new EPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final EPoleBrand brandWahid = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}");
        brand = storeNew(brandWahid);
        final EPole poleWahid = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Pole-Wahid\", \"description\":\"aa\"}");
        final EPole poleThanie = EPole.ofJsonObjStr(
                "{\"id\":1, \"type\":\"Pole-Thanie\"}");
        poleWahid.setBrand(brand);
        poleThanie.setBrand(brand);
        testPoles = storeNews(List.of(poleWahid, poleThanie));
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

        JsonTestUtil.test(resBody, "[{\"id\":1, \"type\":\"Pole-Wahid\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + " \"description\":\"aa\"},"
                + "{\"id\":2, \"type\":\"Pole-Thanie\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + "\"description\":null}]");
    }

    @Test
    void getAllEPoleBrandValidateDatabaseValues() throws Exception {
        final EPole poleWahid = testPoles.get(0);
        final EPole poleThanie = testPoles.get(1);

        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(testPoles.size())))
                .andExpect(jsonPathIdOfModelId("$[0].id", poleWahid))
                .andExpect(jsonPath("$[0].type", is(poleWahid.getType())))
                .andExpect(jsonPath(
                        "$[0].description", is(poleWahid.getDescription())))
                .andExpect(jsonPathIdOfModelId(
                        "$[0].brand.id", poleWahid.getBrand()))
                .andExpect(jsonPath(
                        "$[0].brand.name", is(poleWahid.getBrand().getName())))
                .andExpect(jsonPathIdOfModelId("$[1].id", poleThanie))
                .andExpect(jsonPath("$[1].type", is(poleThanie.getType())))
                .andExpect(jsonPath(
                        "$[1].description", is(poleThanie.getDescription())))
                .andExpect(jsonPathIdOfModelId(
                        "$[1].brand.id", poleWahid.getBrand()))
                .andExpect(jsonPath(
                        "$[1].brand.name",
                        is(poleThanie.getBrand().getName())));
    }
}
