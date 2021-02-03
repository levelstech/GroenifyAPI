package com.groenify.api.loader.success;

import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.repository.epole.EPoleRepository;
import com.groenify.api.loader.FactorPriceAnswerLoaderTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EPoleLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private EPoleRepository repository;

    @Test
    @Override
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
        final EPole ePole = repository.findAll().iterator().next();

        ePoleAssertions(ePole);
    }

    static void ePoleAssertions(final EPole ePole) {
        final EPoleBrand brand = ePole.getBrand();
        Assertions.assertThat(ePole.getType()).isEqualTo("Eve Single Pro line");
        Assertions.assertThat(brand.getName()).isEqualTo("Alfen");

    }

}
