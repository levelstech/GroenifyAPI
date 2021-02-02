package com.groenify.api.loaded;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.repository.company.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyLoadedTest implements LoadedTest {

    @Autowired
    private CompanyRepository repository;

    @Test
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
        final Company company = repository.findAll().iterator().next();

        Assertions.assertThat(company.getName()).isEqualTo("Vandebron");
        Assertions.assertThat(company.getUrl()).isEqualTo("");
    }

}
