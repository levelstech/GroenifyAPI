package com.groenify.api.rest;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.EPoleBrand;
import com.groenify.api.exceptions.ExceptionCatcher;
import com.groenify.api.framework.resolver.EPoleBrandInPathResolver;
import com.groenify.api.repository.EPoleBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class EPoleBrandEndpointGeyByIdTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epole_brands";
    private static EPoleBrand testBrand;
    private static Long brandId;

    @Autowired
    private EPoleBrandRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + brandId;
    }

    protected void setUpMock() {
        final EPoleBrandEndpoint endpoint =
                new EPoleBrandEndpoint(repository);
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new EPoleBrandInPathResolver(repository))
                .setControllerAdvice(new ExceptionCatcher());
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
    void getEPoleBrandByIdValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Brand-Wahid\"}");
    }

    @Test
    void getEPoleBrandByIdValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", testBrand))
                .andExpect(jsonPath("$.name", is(testBrand.getName())));
    }

    @Test
    void getEPoleBrandByIdNotFound() throws Exception {
        brandId = -1L;
        getMockMvc()
                .perform(get(getEndpoint()))
                .andExpect(status().isNotFound());
    }
}