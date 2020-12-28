package com.groenify.api.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ListUtilTest {

    @Test
    void iterableToList() {
        final Set<String> test = Set.of("a", "b", "c");
        final List<String> testList = ListUtil.iterableToList(test);

        assertThat(test).containsAll(testList);
        assertThat(testList).containsAll(test);
    }
}