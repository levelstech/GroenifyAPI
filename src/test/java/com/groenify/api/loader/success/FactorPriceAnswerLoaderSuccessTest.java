package com.groenify.api.loader.success;

import com.groenify.api.loader.FactorPriceAnswerLoaderTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public abstract class FactorPriceAnswerLoaderSuccessTest
        extends FactorPriceAnswerLoaderTest {

    private static final String RESOURCE = "data/test_prices.csv";

    protected final String getResource() {
        return RESOURCE;
    }

}
