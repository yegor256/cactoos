/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.math.BigDecimal;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;

/**
 * Test case for {@link DivisionOf}.
 *
 * @since 0.49.2
 */
final class DivisionOfTest {

    @Test
    void dividesIntNumbers() {
        new Assertion<>(
            "Must divide int numbers",
            new DivisionOf(4, 2).intValue(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    void dividesLongNumbers() {
        new Assertion<>(
            "Must divide long numbers",
            new DivisionOf(4L, 2L).longValue(),
            new IsEqual<>(2L)
        ).affirm();
    }

    @Test
    void dividesFloatNumbers() {
        new Assertion<>(
            "Must divide float numbers",
            new DivisionOf(2f, 4f).floatValue(),
            new IsEqual<>(0.5f)
        ).affirm();
    }

    @Test
    void dividesDoubleNumbers() {
        new Assertion<>(
            "Must divide double numbers",
            new DivisionOf(2d, 4d).doubleValue(),
            new IsEqual<>(0.5d)
        ).affirm();
    }

    @Test
    void dividesBigDecimalNumbers() {
        new Assertion<>(
            "Must divide BigDecimal numbers",
            new DivisionOf(BigDecimal.valueOf(2d), BigDecimal.valueOf(4d)),
            new IsNumber(0.5)
        ).affirm();
    }
}
