/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.math.BigDecimal;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsNumber;

/**
 * Test case for {@link DivisionOf}.
 *
 * @since 0.49.2
 */
final class DivisionOfTest {

    @Test
    void dividesIntNumbers() {
        MatcherAssert.assertThat(
            "Must divide int numbers",
            new DivisionOf(4, 2).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    void dividesLongNumbers() {
        MatcherAssert.assertThat(
            "Must divide long numbers",
            new DivisionOf(4L, 2L).longValue(),
            new IsEqual<>(2L)
        );
    }

    @Test
    void dividesFloatNumbers() {
        MatcherAssert.assertThat(
            "Must divide float numbers",
            new DivisionOf(2f, 4f).floatValue(),
            new IsEqual<>(0.5f)
        );
    }

    @Test
    void dividesDoubleNumbers() {
        MatcherAssert.assertThat(
            "Must divide double numbers",
            new DivisionOf(2d, 4d).doubleValue(),
            new IsEqual<>(0.5d)
        );
    }

    @Test
    void dividesBigDecimalNumbers() {
        MatcherAssert.assertThat(
            "Must divide BigDecimal numbers",
            new DivisionOf(BigDecimal.valueOf(2d), BigDecimal.valueOf(4d)),
            new IsNumber(0.5)
        );
    }
}
