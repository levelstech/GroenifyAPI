package com.example.groenify_api.rest;

import com.example.groenify_api.database.IdModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@DataJpaTest(showSql = false)
@EnableAutoConfiguration
public abstract class EndpointTest {

    @Autowired
    private TestEntityManager entityManager;
    private MockMvc mockMvc;

    public void setMockMvc(final MockMvc var) {
        this.mockMvc = var;
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public void setEntityManager(final TestEntityManager var) {
        this.entityManager = var;
    }

    public TestEntityManager getEntityManager() {
        return entityManager;
    }

    abstract String getEndpoint();

    protected <T extends IdModel> T storeNew(final T model) {
        model.setId(null);
        return getEntityManager().persist(model);
    }
    protected <T extends IdModel> List<T> storeNews(final List<T> model) {
       return model.stream()
               .map(this::storeNew)
               .collect(Collectors.toList());
    }

    @Test
    void smokeTest() throws Exception {
        getMockMvc().perform(options(getEndpoint()))
                .andExpect(header()
                        .exists(HttpHeaders.ALLOW));
    }

}
