package com.groenify.api.rest;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.EPoleBrand;
import com.groenify.api.framework.resolver.EPoleBrandInPathResolver;
import com.groenify.api.repository.EPoleBrandRepository;
import com.groenify.api.service.EPoleBrandService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class EPoleBrandEndpointDeleteTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epole_brands";
    private static Long brandId;

    @Autowired
    private EPoleBrandRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + brandId;
    }

    protected void setUpMock() {
        final EPoleBrandEndpoint endpoint =
                new EPoleBrandEndpoint(new EPoleBrandService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new EPoleBrandInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final EPoleBrand brandWahid = EPoleBrand.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Brand-Wahid\"}");
        final EPoleBrand testBrand = storeNew(brandWahid);
        brandId = testBrand.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void postEPoleBrandDeleteValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "true");
        Assertions.assertThat(repository.existsByNameIgnoreCase("Brand-Wahid")).isFalse();
    }

    @Test
    void postEPoleBrandDeleteValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertThat(repository.existsByNameIgnoreCase("Brand-Wahid")).isFalse();
    }

    @Test
    void postEPoleBrandCreateInvalidPost() throws Exception {
        brandId = -1L;
        getMockMvc()
                .perform(delete(getEndpoint()))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase("Brand-Wahid")).isTrue();
    }
}