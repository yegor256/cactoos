/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test Case for {@link Sorted}.
 * @since 0.19
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SortedTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void sortsIterable() {
        new Assertion<>(
            "Must sort elements in iterator",
            new IterableOf<>(
                new Sorted<>(
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new HasValues<>(
                "four", "one", "three", "two"
            )
        ).affirm();
    }

}
