/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link And}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class AndTest {

    @Test
    void allTrue() {
        MatcherAssert.assertThat(
            "Each object must be True",
            new And(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void oneFalse() {
        MatcherAssert.assertThat(
            "One object must be False",
            new And(
                new True(),
                new False(),
                new True()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void allFalse() {
        MatcherAssert.assertThat(
            "Each object must be False",
            new And(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void emptyIterator() {
        MatcherAssert.assertThat(
            "Iterator must be empty",
            new And(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(true)
        );
    }

    @Test
    void appliesFuncToIterable() {
        MatcherAssert.assertThat(
            "lambda should be called for iterable",
            new And(
                input -> input > 0,
                new IterableOf<>(1, -1, 0)
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void appliesFuncToVarargs() {
        MatcherAssert.assertThat(
            "lambda should be called for varargs",
            new And(
                input -> input > 0,
                -1, -2, 0
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void comparesAgainstMultipleTrueConditions() {
        MatcherAssert.assertThat(
            "Can't compare subject with true conditions",
            new And(
                3,
                input -> input > 0,
                input -> input > 5,
                input -> input > 4
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void comparesAgainstMultipleFalseConditions() {
        MatcherAssert.assertThat(
            "Can't compare subject with false conditions",
            new And(
                "cactoos",
                input -> input.contains("singleton"),
                input -> input.contains("static")
            ),
            new HasValue<>(false)
        );
    }
}
