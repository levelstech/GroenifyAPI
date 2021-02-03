package com.groenify.api.loader.success;

import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.repository.factor.FactorRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class FactorLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private FactorRepository repository;

    @Test
    @Override
    public void testAll() {
        Assertions.assertThat(repository.findAll()).hasSize(4);
        final List<Factor> factors = Lists.newArrayList(repository.findAll());

        factorOneAssertions(factors.get(0));
        factorTwoAssertions(factors.get(1));
        factorThreeAssertions(factors.get(2));
        factorFourAssertions(factors.get(3));
    }

    static void factorOneAssertions(final Factor factor) {
        Assertions.assertThat(factor.getName()).isEqualTo("soort_laadpunt");
        Assertions.assertThat(factor.getQuestion())
                .isEqualTo("Welk soort laadpunt wilt u hebben?");
        Assertions.assertThat(factor.getTypeValue()).isEqualTo(2L);
        Assertions.assertThat(factor.getDescription()).isEqualTo("");
    }

    static void factorTwoAssertions(final Factor factor) {
        Assertions.assertThat(factor.getName()).isEqualTo("vaste_laadkabel");
        Assertions.assertThat(factor.getQuestion())
                .isEqualTo("Wilt u een vaste laadpaal?");
        Assertions.assertThat(factor.getTypeValue()).isEqualTo(1L);
        Assertions.assertThat(factor.getDescription()).isEqualTo("");
    }

    static void factorThreeAssertions(final Factor factor) {
        Assertions.assertThat(factor.getName()).isEqualTo("lengte_laadkabel");
        Assertions.assertThat(factor.getQuestion())
                .isEqualTo("Welke lengte zou u de laadkabel willen?");
        Assertions.assertThat(factor.getTypeValue()).isEqualTo(3L);
        Assertions.assertThat(factor.getDescription()).isEqualTo("");
    }

    static void factorFourAssertions(final Factor factor) {
        Assertions.assertThat(factor.getName()).isEqualTo("installatie_pakket");
        Assertions.assertThat(factor.getQuestion())
                .isEqualTo("Wat is de lengte van uw meterkast tot "
                        + "aan de laadpaal? En hoeveel meter is ondergronds?");
        Assertions.assertThat(factor.getTypeValue()).isEqualTo(4L);
        Assertions.assertThat(factor.getDescription()).isEqualTo("");
    }

}
