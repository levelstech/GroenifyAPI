package com.groenify.api;

import com.groenify.api.database.repository.company.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GroenifyApiApplicationTests {

    @Autowired
    private CompanyRepository repository;

    @Test
    void testAllCompanies() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
    }

}
