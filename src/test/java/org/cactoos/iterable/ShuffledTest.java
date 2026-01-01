/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Random;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test Case for {@link Shuffled}.
 *
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ShuffledTest {

    @Test
    void shuffleArray() {
        new Assertion<>(
            "Must shuffle an iterable",
            new Shuffled<>(
                new IterableOf<>(
                    6, 2, 5
                )
            ),
            new HasValues<>(2, 5, 6)
        ).affirm();
    }

    @Test
    void shuffleCollection() {
        new Assertion<>(
            "Must shuffle elements in collection",
            new Shuffled<>(new ListOf<>(1, 2, 0, -1)),
            new HasValues<>(1, 2, 0, -1)
        ).affirm();
    }

    @Test
    void shufflesIterable() {
        new Assertion<>(
            "Must shuffle elements in iterable",
            new Shuffled<>(new IterableOf<>(1, 2, 0, -1)),
            new HasValues<>(1, 2, 0, -1)
        ).affirm();
    }

    @Test
    void shuffleIterableWithRandomized() {
        new Assertion<>(
            "Must shuffle elements with randomizer",
            new Shuffled<>(
                new Random(0L),
                new IterableOf<>(
                    3, 2, 1
                )
            ),
            new IsEqual<>(
                new IterableOf<>(1, 2, 3)
            )
        ).affirm();
    }

    @Test
    void shufflesVarargs() {
        new Assertion<>(
            "Must shuffle elements from varargs",
            new Shuffled<>(1, 2, 3, 4, 4, 5),
            new HasValues<>(1, 2, 3, 4, 4, 5)
        ).affirm();
    }
}
