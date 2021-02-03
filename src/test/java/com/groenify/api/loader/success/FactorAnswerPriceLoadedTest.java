package com.groenify.api.loader.success;

import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.database.repository.price.FactorAnswerPriceRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class FactorAnswerPriceLoadedTest extends FactorPriceAnswerLoaderSuccessTest {

    @Autowired
    private FactorAnswerPriceRepository repository;

    @Test
    @Override
    public void testAll() {

        Assertions.assertThat(repository.findAll()).hasSize(9);
        final List<FactorAnswerPrice> prices = Lists.newArrayList(repository.findAll());
        assertionsFactorAnswerPriceOne(prices.get(0));
        assertionsFactorAnswerPriceTwo(prices.get(1));
        assertionsFactorAnswerPriceThree(prices.get(2));
        assertionsFactorAnswerPriceFour(prices.get(3));
        assertionsFactorAnswerPriceFive(prices.get(4));
        assertionsFactorAnswerPriceSix(prices.get(5));
        assertionsFactorAnswerPriceSeven(prices.get(6));
        assertionsFactorAnswerPriceEight(prices.get(7));
        assertionsFactorAnswerPriceNine(prices.get(8));
    }

    static void assertionsFactorAnswerPriceOne(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(172d);
        FactorAnswerLoadedTest.assertionsFactorAnswerOne(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceTwo(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(0);
        FactorAnswerLoadedTest.assertionsFactorAnswerTwo(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }


    static void assertionsFactorAnswerPriceThree(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(10);
        FactorAnswerLoadedTest.assertionsFactorAnswerThree(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceFour(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(0);
        FactorAnswerLoadedTest.assertionsFactorAnswerFour(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceFive(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(0);
        FactorAnswerLoadedTest.assertionsFactorAnswerFive(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceSix(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(10);
        FactorAnswerLoadedTest.assertionsFactorAnswerSix(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceSeven(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(593);
        FactorAnswerLoadedTest.assertionsFactorAnswerSeven(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceEight(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(793);
        FactorAnswerLoadedTest.assertionsFactorAnswerEight(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

    static void assertionsFactorAnswerPriceNine(final FactorAnswerPrice price) {
        Assertions.assertThat(price.getPrice()).isEqualTo(893);
        FactorAnswerLoadedTest.assertionsFactorAnswerNine(price.getFactorAnswer());
        CompanyEPoleLoadedTest.companyEPoleAssertions(price.getPole());
    }

}
