package com.groenify.api.loader.success;

import com.groenify.api.loader.FactorPriceAnswerLoaderTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public abstract class FactorPriceAnswerLoaderSuccessTest
        extends FactorPriceAnswerLoaderTest {

    private final static String RESOURCE = "data/test_prices.csv";

    protected String getResource() {
        return RESOURCE;
    }

}
