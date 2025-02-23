/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Random;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test Case for {@link Shuffled}.
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ShuffledTest {

    @Test
    void shuffleIterable() {
        new Assertion<>(
            "Must shuffle elements in iterator",
            new IterableOf<>(
                new Shuffled<>(
                    new IteratorOf<>(
                        "a", "b"
                    )
                )
            ),
            new HasValues<>(
                "a", "b"
            )
        ).affirm();
    }

    @Test
    void shuffleIterableWithRandomized() {
        new Assertion<>(
            "Must shuffle elements with randomizer",
            new IterableOf<>(
                () -> new Shuffled<>(
                    new Random(0L),
                    new IteratorOf<>(
                        "C", "B", "A"
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>("A", "B", "C")
            )
        ).affirm();
    }
}
