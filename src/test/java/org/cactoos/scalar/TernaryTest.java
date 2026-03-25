/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.text.FormattedText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Ternary}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TernaryTest {

    @Test
    void conditionTrueScalar() {
        MatcherAssert.assertThat(
            "Must work with true scalar condition",
            new Ternary<>(
                new True(),
                6,
                16
            ),
            new HasValue<>(6)
        );
    }

    @Test
    void conditionFalseScalar() {
        MatcherAssert.assertThat(
            "Must work with false scalar condition",
            new Ternary<>(
                new False(),
                6,
                16
            ),
            new HasValue<>(16)
        );
    }

    @Test
    void conditionStatic() {
        MatcherAssert.assertThat(
            "Must work with primitive static condition",
            new Ternary<>(
                true,
                6,
                16
            ),
            new HasValue<>(6)
        );
    }

    @Test
    void consequentScalar() {
        MatcherAssert.assertThat(
            "Must work with scalar consequent and alternative",
            new Ternary<>(
                true,
                new Constant<>(6),
                new Constant<>(16)
            ),
            new HasValue<>(6)
        );
    }

    @Test
    void inputStatic() {
        MatcherAssert.assertThat(
            "Must call the functions with the input",
            new Ternary<>(
                5,
                input -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new HasValue<>(6)
        );
    }

    @Test
    void inputScalar() {
        MatcherAssert.assertThat(
            "Must call the functions with the input scalar value",
            new Ternary<>(
                new Constant<>(5),
                (Integer input) -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new HasValue<>(6)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void inputScalarValueConserved() {
        MatcherAssert.assertThat(
            "Must conserve the same scalar value for each whole evaluation",
            new Ternary<>(
                new ScalarOf<>(new AtomicInteger(0)::incrementAndGet),
                (Integer i) -> i == 1,
                i -> new FormattedText("%d equals 1", i),
                i -> new FormattedText("else: %d", i)
            ),
            new AllOf<>(
                new HasValue<>(new IsText("1 equals 1")),
                new HasValue<>(new IsText("else: 2")),
                new HasValue<>(new IsText("else: 3"))
            )
        );
    }
}
