package com.groenify.api.rest.epole;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;
import com.groenify.api.framework.annotation.resolver.EPoleInPathResolver;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class EPoleEndpointUpdateTest extends EndpointTest {

    private static final String ENDPOINT = "/api/v1/epoles";
    private static Long poleId;
    private static EPole testPole;

    @Autowired
    private EPoleRepository repository;

    @Override
    protected String getEndpoint() {
        return ENDPOINT + "/" + poleId;
    }

    protected void setUpMock() {
        final EPoleEndpoint endpoint =
                new EPoleEndpoint(new EPoleService(repository));
        final StandaloneMockMvcBuilder mvcBuilder =
                MockMvcBuilders.standaloneSetup(endpoint);

        mvcBuilder.setCustomArgumentResolvers(
                new EPoleInPathResolver(repository));

        setMockMvc(mvcBuilder.build());
    }

    protected void setUpData() {

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

    @Test
    void getEFromPoleValidateJsonKeyNames() throws Exception {

        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid(1)\","
                                + "\"description\":\"(1)\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, "{\"id\":1, \"type\":\"Pole-Wahid(1)\","
                + "\"brand\":{\"id\":2, \"name\":\"Brand-Thanie\"},"
                + " \"description\":\"(1)\"}");

        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid")).isFalse();
        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid(1)")).isTrue();
    }

    @Test
    void getEPoleValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Pole-Wahid(1)\","
                                + "\"description\":\"(1)\"}"))
                .andExpect(status().isOk())
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$.id", testPole))
                .andExpect(jsonPath("$.type", is("Pole-Wahid(1)")))
                .andExpect(jsonPath("$.description", is("(1)")))
                .andExpect(RestTestUtil.jsonPathIdOfModelId("$.brand.id", testPole.getBrand()))
                .andExpect(jsonPath("$.brand.name", is(testPole.getBrand().getName())));

        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid")).isFalse();
        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid(1)")).isTrue();
    }

    @Test
    void getEPoleInvalid() throws Exception {
        poleId = -1L;

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pole-Wahid(1)\"}"))
                .andExpect(status().isNotFound());

        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid")).isTrue();
        Assertions.assertThat(repository.existsByTypeIgnoreCase("Pole-Wahid(1)")).isFalse();

        poleId = testPole.getId();
    }
}