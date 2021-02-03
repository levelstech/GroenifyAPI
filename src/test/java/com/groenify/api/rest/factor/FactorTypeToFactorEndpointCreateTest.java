package com.groenify.api.rest.factor;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.model.factor.FactorType;
import com.groenify.api.framework.annotation.resolver.FactorTypeInPathResolver;
import com.groenify.api.database.repository.factor.FactorRepository;
import com.groenify.api.database.repository.factor.FactorTypeRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.database.service.factor.FactorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorTypeToFactorEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factor_types";
    private static Long typeId;
    private static FactorType testType;

    @Autowired
    private FactorRepository repository;
    @Autowired
    private FactorTypeRepository typeRepository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + typeId + "/factors";
    }

    private static void setTestType(final FactorType var) {
        FactorTypeToFactorEndpointCreateTest.testType = var;
    }

    private static void setTypeId(final Long var) {
        FactorTypeToFactorEndpointCreateTest.typeId = var;
    }

    protected void setUpMock() {
        final FactorTypeToFactorEndpoint endpoint =
                new FactorTypeToFactorEndpoint(new FactorService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorTypeInPathResolver(typeRepository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        setTestType(storeNew(typeWahid));
        setTypeId(testType.getId());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void getEFromTypeValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid\","
                                + "\"question\":\")?\","
                                + "\"required\":false,"
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":2,\"type\":"
                + "{\"id\":2,\"name\":\"Factor-Wahid\",\"description\":null},"
                + "\"name\":\"Factor-Wahid(1)\","
                + "\"required\":false,"
                + "\"question\":\"Q(1)?\","
                + "\"description\":\"bb\"}");

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid")).isTrue();
    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid\","
                                + "\"question\":\"Q?\","
                                + "\"required\":false,"
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Factor-Wahid")))
                .andExpect(jsonPath("$.question", is("Q?")))
                .andExpect(jsonPath("$.required", is(false)))
                .andExpect(jsonPathIdOfModelId("$.type.id", testType))
                .andExpect(jsonPath("$.type.name", is(testType.getName())))
                .andExpect(jsonPath("$.description", is("bb")));

        Assertions.assertThat(repository.existsByNameIgnoreCase(
                "Factor-Wahid")).isTrue();
    }
}
