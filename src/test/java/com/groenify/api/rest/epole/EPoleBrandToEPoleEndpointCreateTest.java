package com.groenify.api.rest.epole;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.EPoleBrandInPathResolver;
import com.groenify.api.repository.epole.EPoleBrandRepository;
import com.groenify.api.repository.epole.EPoleRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.rest.RestTestUtil;
import com.groenify.api.service.epole.EPoleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class EPoleBrandToEPoleEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epole_brands";
    private static Long brandId;
    private static EPoleBrand testBrand;

    @Autowired
    private EPoleRepository repository;
    @Autowired
    private EPoleBrandRepository brandRepository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + brandId + "/epoles";
    }

    protected void setUpMock() {
        final EPoleBrandToEPoleEndpoint endpoint =
                new EPoleBrandToEPoleEndpoint(new EPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new EPoleBrandInPathResolver(brandRepository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final EPoleBrand brandWahid = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}");
        testBrand = storeNew(brandWahid);
        brandId = testBrand.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getEFromPoleValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid\","
                                + "\"description\":\"(1)\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"type\":\"Pole-Wahid(1)\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + "\"description\":\"(1)\"}");

        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid")).isTrue();
    }

    @Test
    void getEPoleValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid\","
                                + "\"description\":\"description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type", is("Pole-Wahid")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$.brand.id", testBrand))
                .andExpect(jsonPath("$.brand.name", is(testBrand.getName())));

        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid")).isTrue();
    }

}