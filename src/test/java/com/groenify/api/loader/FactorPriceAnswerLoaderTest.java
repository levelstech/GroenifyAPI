package com.groenify.api.loader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public abstract class FactorPriceAnswerLoaderTest {

    @Autowired
    private FactorPriceAnswerLoader loader;

    @Test
    protected abstract void testAll();

    protected abstract String getResource();

    @BeforeAll
    final void setup() {
        FactorPriceAnswerLoader.setResource(getResource());
        loader.loadOnReady();
    }

}
