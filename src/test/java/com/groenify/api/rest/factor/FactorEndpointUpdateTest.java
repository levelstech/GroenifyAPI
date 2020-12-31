package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.FactorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorEndpointUpdateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private static Long factorId;
    private static Factor testFactor;

    @Autowired
    private FactorRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + factorId;
    }

    protected void setUpMock() {
        final FactorEndpoint endpoint =
                new FactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        final FactorType type = storeNew(typeWahid);
        final Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid\", "
                        + "\"question\":\"Q?\","
                        + "\"description\":\"aa\"}");
        factorWahid.setType(type);
        testFactor = storeNew(factorWahid);
        factorId = testFactor.getId();

    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Type-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid(1)\","
                + "\"question\":\"Q(1)?\","
                + "\"description\":\"bb\"}");

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isTrue();
    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", testFactor))
                .andExpect(jsonPath("$.name", is("Factor-Wahid(1)")))
                .andExpect(jsonPath("$.question", is("Q(1)?")))
                .andExpect(jsonPathIdOfModelId(
                        "$.type.id", testFactor.getType()))
                .andExpect(jsonPath(
                        "$.type.name", is(testFactor.getType().getName())))
                .andExpect(jsonPath("$.description", is("bb")));

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid")).isFalse();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isTrue();
    }

    @Test
    void getFactorInvalid() throws Exception {
        factorId = -1L;

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid")).isTrue();
        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid(1)")).isFalse();

        factorId = testFactor.getId();
    }
}
