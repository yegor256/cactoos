/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.RangeOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Folded}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class FoldedTest {

    @Test
    void skipIterable() {
        new Assertion<>(
            "Must fold elements in iterable",
            new Folded<>(
                0L, Long::sum,
                new HeadOf<>(
                    10,
                    new RangeOf<>(0L, Long.MAX_VALUE, value -> value + 1L)
                )
            ),
            new HasValue<>(45L)
        ).affirm();
    }

    @Test
    void constructedFromVarargs() throws Exception {
        new Assertion<>(
            "Must fold elements in vararg array",
            new Folded<>(
                0L,
                (first, second) -> first + second,
                1, 2, 3, 4, 5
            ).value(),
            new IsEqual<>(15L)
        ).affirm();
    }
}
