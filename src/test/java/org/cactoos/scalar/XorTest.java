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
 * Test case for {@link Xor}.
 * @since 0.48
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyMethods"})
final class XorTest {

    @Test
    void trueTrue() {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void falseTrue() {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void treuFalse() {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new False()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void falseFalse() {
        new Assertion<>(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void singleTrue() {
        new Assertion<>(
            "Single True must be True",
            new Xor(
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void singleFalse() {
        new Assertion<>(
            "Single False must be False",
            new Xor(
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void emptyIterable() {
        new Assertion<>(
            "Empty iterable must be False",
            new Xor(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void oddNumberOfTrue() {
        new Assertion<>(
            "Odd number of True must be True",
            new Xor(
                new False(),
                new False(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void evenNumberOfTrue() {
        new Assertion<>(
            "Even number of True must be False",
            new Xor(
                new False(),
                new True(),
                new True()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allFalse() {
        new Assertion<>(
            "All False must be False",
            new Xor(
                new False(),
                new False(),
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void allTrue() {
        new Assertion<>(
            "Odd number of True must be True",
            new Xor(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        ).affirm();
    }
}
