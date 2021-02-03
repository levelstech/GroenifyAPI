package com.groenify.api.rest;

import com.groenify.api.ModelCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
public abstract class EndpointTest implements ModelCreator {

    @Autowired
    private TestEntityManager entityManager;
    private MockMvc mockMvc;

    public final void setMockMvc(final MockMvc var) {
        this.mockMvc = var;
    }

    public final MockMvc getMockMvc() {
        return mockMvc;
    }

    @Override
    public final void setEntityManager(final TestEntityManager var) {
        this.entityManager = var;
    }

    @Override
    public final TestEntityManager getEntityManager() {
        return entityManager;
    }

    protected abstract String getEndpoint();

    @Test
    void smokeTest() throws Exception {
        getMockMvc().perform(options(getEndpoint()))
                .andExpect(header()
                        .exists(HttpHeaders.ALLOW));
    }

    @AfterAll
    public static void closeTestSuite() {
        TestRestObjectGetterUtil.tearDown();
    }

}
