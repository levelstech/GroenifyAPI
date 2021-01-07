package com.groenify.api.rest.factor.answer;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.config.ApplicationLoader;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.framework.annotation.resolver.FactorInPathResolver;
import com.groenify.api.repository.factor.FactorRepository;
import com.groenify.api.repository.factor.FactorTypeRepository;
import com.groenify.api.repository.factor.answer.FactorAnswerRepository;
import com.groenify.api.rest.EndpointTest;
import com.groenify.api.service.factor.answer.FactorAnswerService;
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

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
class FactorAnswerEndpointCreateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/factors";
    private static Long factorType;
    private static Long factorId;
    @Autowired
    private FactorTypeRepository typeRepository;
    @Autowired
    private FactorRepository factorRepository;
    @Autowired
    private FactorAnswerRepository repository;

    public static Long getFactorId() {
        return factorId;
    }

    public static void setFactorId(final Long var) {
        FactorAnswerEndpointCreateTest.factorId = var;
    }

    public static Long getFactorType() {
        return factorType;
    }

    public static void setFactorType(final Long var) {
        FactorAnswerEndpointCreateTest.factorType = var;
    }

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + factorId + "/answers";
    }

    protected void setUpMock() {
        final FactorAnswerEndpoint endpoint =
                new FactorAnswerEndpoint(new FactorAnswerService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new FactorInPathResolver(factorRepository));
        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

        ApplicationLoader.loadFactorTypeEnumerators(typeRepository);

        final FactorType typeWahid = FactorType.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Type-Wahid\"}");
        final FactorType type = storeNew(typeWahid);
        Factor factorWahid = Factor.ofJsonObjStr(
                "{\"id\":1, \"name\":\"Factor-Wahid1\","
                        + "\"question\":\"Q11?\","
                        + "\"description\":\"dd\"}");
        factorWahid.setType(type);
        factorWahid = storeNew(factorWahid);
        setFactorId(factorWahid.getId());
        setFactorType(FactorTypeEnum.BOOLEAN_QUESTION.getNumber());
    }

    @BeforeEach
    protected final void setUpTest() {
        setUpData();
        setUpMock();
    }

    @Test
    void postFactorTypeCreateValidateJsonKeyNames() throws Exception {
        final String resBody = getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":false}")
                        .queryParam("type", String.valueOf(factorType)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":3,"
                + "\"factor\":"
                + "{\"id\":2,"
                + "\"type\":{\"id\":10,\"name\":\"Type-Wahid\","
                + "\"description\":null},"
                + "\"name\":\"Factor-Wahid1\","
                + "\"question\":\"Q11?\","
                + "\"description\":\"dd\"},"
                + "\"type\":6,"
                + "\"answer\":true}");
    }

    @Test
    void postFactorTypeCreateValidateDatabaseValues() throws Exception {
        getMockMvc()
                .perform(post(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":false}")
                        .queryParam("type", String.valueOf(factorType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.factor.id", is(factorId.intValue())))
                .andExpect(jsonPath("$.type", is(factorType.intValue())))
                .andExpect(jsonPath("$.answer", is(false)));
    }

}
