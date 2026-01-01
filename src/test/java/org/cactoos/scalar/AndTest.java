/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link And}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class AndTest {

    @Test
    void allTrue() {
        new Assertion<>(
            "Each object must be True",
            new And(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void oneFalse() {
        new Assertion<>(
            "One object must be False",
            new And(
                new True(),
                new False(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allFalse() {
        new Assertion<>(
            "Each object must be False",
            new And(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void emptyIterator() {
        new Assertion<>(
            "Iterator must be empty",
            new And(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void testFuncIterable() {
        new Assertion<>(
            "lambda should be called for iterable",
            new And(
                input -> input > 0,
                new IterableOf<>(1, -1, 0)
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testFuncVarargs() {
        new Assertion<>(
            "lambda should be called for varargs",
            new And(
                input -> input > 0,
                -1, -2, 0
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionTrue() {
        new Assertion<>(
            "Can't compare subject with true conditions",
            new And(
                3,
                input -> input > 0,
                input -> input > 5,
                input -> input > 4
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionFalse() {
        new Assertion<>(
            "Can't compare subject with false conditions",
            new And(
                "cactoos",
                input -> input.contains("singleton"),
                input -> input.contains("static")
            ),
            new HasValue<>(false)
        ).affirm();
    }
}
