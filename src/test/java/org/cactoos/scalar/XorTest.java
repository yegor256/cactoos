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
 * Test case for {@link Xor}.
 * @since 0.48
 */
@SuppressWarnings("PMD.TooManyMethods")
final class XorTest {

    @Test
    void trueTrue() {
        MatcherAssert.assertThat(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new True()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void falseTrue() {
        MatcherAssert.assertThat(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new True()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void treuFalse() {
        MatcherAssert.assertThat(
            "Either one, but not both nor none",
            new Xor(
                new True(),
                new False()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void falseFalse() {
        MatcherAssert.assertThat(
            "Either one, but not both nor none",
            new Xor(
                new False(),
                new False()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void singleTrue() {
        MatcherAssert.assertThat(
            "Single True must be True",
            new Xor(
                new True()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void singleFalse() {
        MatcherAssert.assertThat(
            "Single False must be False",
            new Xor(
                new False()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void emptyIterable() {
        MatcherAssert.assertThat(
            "Empty iterable must be False",
            new Xor(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(false)
        );
    }

    @Test
    void oddNumberOfTrue() {
        MatcherAssert.assertThat(
            "Odd number of True must be True",
            new Xor(
                new False(),
                new False(),
                new True()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void evenNumberOfTrue() {
        MatcherAssert.assertThat(
            "Even number of True must be False",
            new Xor(
                new False(),
                new True(),
                new True()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void allFalse() {
        MatcherAssert.assertThat(
            "All False must be False",
            new Xor(
                new False(),
                new False(),
                new False()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void allTrue() {
        MatcherAssert.assertThat(
            "Odd number of True must be True",
            new Xor(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        );
    }
}
