/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "Must work with true scalar condition",
            new Ternary<>(
                new True(),
                6,
                16
            ),
            new HasValue<>(6)
        ).affirm();
    }

    @Test
    void conditionFalseScalar() {
        new Assertion<>(
            "Must work with false scalar condition",
            new Ternary<>(
                new False(),
                6,
                16
            ),
            new HasValue<>(16)
        ).affirm();
    }

    @Test
    void conditionStatic() {
        new Assertion<>(
            "Must work with primitive static condition",
            new Ternary<>(
                true,
                6,
                16
            ),
            new HasValue<>(6)
        ).affirm();
    }

    @Test
    void consequentScalar() {
        new Assertion<>(
            "Must work with scalar consequent and alternative",
            new Ternary<>(
                true,
                new Constant<>(6),
                new Constant<>(16)
            ),
            new HasValue<>(6)
        ).affirm();
    }

    @Test
    void inputStatic() {
        new Assertion<>(
            "Must call the functions with the input",
            new Ternary<>(
                5,
                input -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new HasValue<>(6)
        ).affirm();
    }

    @Test
    void inputScalar() {
        new Assertion<>(
            "Must call the functions with the input scalar value",
            new Ternary<>(
                new Constant<>(5),
                (Integer input) -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new HasValue<>(6)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void inputScalarValueConserved() {
        new Assertion<Scalar<Text>>(
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
        ).affirm();
    }
}
