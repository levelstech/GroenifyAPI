package com.groenify.api.rest.factor.answer;

import com.groenify.api.JsonTestUtil;
import com.groenify.api.rest.TestRestObjectGetterUtil;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import static com.groenify.api.rest.RestTestUtil.jsonPathIdOfModelId;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest()
@EnableAutoConfiguration
class FactorAnswerEndpointUpdateTest extends FactorAnswerEndpointById {

    @Test
    void getEFromFactorValidateJsonKeyNames() throws Exception {

        final String resObj = TestRestObjectGetterUtil.
                getJsonResponseObject(FactorAnswerResMo.class, "1");
        final String resBody = getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"answer\":false}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonTestUtil.test(resBody, resObj);


    }

    @Test
    void getFactorValidateDatabaseValues() throws Exception {

        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"answer\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPathIdOfModelId("$.id", getTestAnswer()))
                .andExpect(jsonPathIdOfModelId(
                        "$.factor.id", getTestAnswer().getFactor()))
                .andExpect(jsonPathIdOfModelId(
                        "$.type", getTestAnswer().getType()))
                .andExpect(jsonPath(
                        "$.answer", is(getTestAnswer().getAnswer())));

    }

    @Test
    void getFactorInvalid() throws Exception {
        setAnswerId(-1L);


        getMockMvc()
                .perform(put(getEndpoint())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"Factor-Wahid(1)\","
                                + "\"question\":\"Q(1)?\","
                                + "\"description\":\"bb\"}"))
                .andExpect(status().isNotFound());

        setAnswerId(getTestAnswer().getId());
    }
}
