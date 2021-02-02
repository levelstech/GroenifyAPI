package com.groenify.api.loaded;

import com.groenify.api.database.model.epole.EPole;
import com.groenify.api.database.model.epole.EPoleBrand;
import com.groenify.api.database.repository.epole.EPoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EPoleLoadedTest implements LoadedTest {

    @Autowired
    private EPoleRepository repository;

    @Test
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(1);
        final EPole ePole = repository.findAll().iterator().next();

        final EPoleBrand brand = ePole.getBrand();

        Assertions.assertThat(ePole.getType()).isEqualTo("Eve Single Pro line");
        Assertions.assertThat(brand.getName()).isEqualTo("Alfen");
    }

}
