package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorTypeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorTypeEndpointUpdateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static Long typeId;
    private FactorType testType;

    @Autowired
    private FactorTypeRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + typeId;
    }

    protected void setUpMock() {
        final FactorTypeEndpoint endpoint =
                new FactorTypeEndpoint(new FactorTypeService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(repository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {
        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\","
                        + "\"description\":\"aaaa\"}");
        testType = storeNew(typeWahid);
        typeId = testType.getId();
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void putFactorTypeUpdateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1)\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"name\":\"Type-Wahid(1)\","
                + "\"description\":\"aaaa\"}");
    }

    @Test
    void putFactorTypeUpdateValidateKeyValues() throws Exception {
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1)\","
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.name", is("Type-Wahid(1)")))
                .andExpect(jsonPath("$.description", is("aaaa")));
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Wahid")).isFalse();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Wahid(1)")).isTrue();

        Assertions.assertThat(testType.getName()).isEqualTo("Type-Wahid(1)");
        Assertions.assertThat(testType.getDescription()).isEqualTo("aaaa");
    }

    @Test
    void putFactorTypeUpdateInvalid() throws Exception {
        typeId = -1L;
        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Type-Wahid(1),"
                                + "\"description\":\"aaaa\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Wahid")).isTrue();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Type-Wahid(1)")).isFalse();
        typeId = testType.getId();
    }
}
