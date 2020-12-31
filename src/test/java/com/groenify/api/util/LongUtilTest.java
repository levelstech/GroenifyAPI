package com.groenify.api.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LongUtilTest {

    @Test
    void parseOrDefault() {
        Assertions.assertThat(LongUtil.parseOrDefault("1")).isEqualTo(1L);
        Assertions.assertThat(LongUtil.parseOrDefault("0")).isEqualTo(0L);
        Assertions.assertThat(LongUtil.parseOrDefault("a")).isEqualTo(0L);
    }
}
