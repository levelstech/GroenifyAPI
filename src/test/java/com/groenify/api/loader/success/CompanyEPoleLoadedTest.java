package com.groenify.api.loader.success;

import com.groenify.api.database.model.company.Company;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.repository.company.CompanyEPoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CompanyEPoleLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private CompanyEPoleRepository repository;

    @Test
    @Override
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
        final CompanyEPole companyEPole =
                repository.findAll().iterator().next();

        companyEPoleAssertions(companyEPole);
    }

    static void companyEPoleAssertions(final CompanyEPole companyEPole) {
        final EPole ePole = companyEPole.getEPole();
        final Company company = companyEPole.getCompany();

        EPoleLoadedTest.ePoleAssertions(ePole);
        CompanyLoadedTest.companyAssertions(company);
        Assertions.assertThat(companyEPole.getBasePrice()).isEqualTo(450d);

    }

}
