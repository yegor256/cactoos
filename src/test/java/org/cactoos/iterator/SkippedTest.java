/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Skipped}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SkippedTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void skipIterator() {
        new Assertion<>(
            "Must skip elements in iterator",
            new IterableOf<>(
                new Skipped<>(
                    2,
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new HasValues<>(
                "three",
                "four"
            )
        ).affirm();
    }

    @Test
    void errorSkippedMoreThanExists() {
        new Assertion<>(
            "Must throw an exception",
            () -> new Skipped<>(
                2,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
