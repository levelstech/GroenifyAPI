package com.groenify.api.loader.success;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.repository.company.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CompanyLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private CompanyRepository repository;

    @Test
    @Override
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
        final Company company = repository.findAll().iterator().next();

        companyAssertions(company);
    }

    static void companyAssertions(final Company company) {
        Assertions.assertThat(company.getName()).isEqualTo("Vandebron");
        Assertions.assertThat(company.getUrl()).isEqualTo("");
    }

}
