/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link HeadOf}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HeadOfTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void headIterator() {
        new Assertion<>(
            "Must skip elements in iterator",
            new IterableOf<>(
                new HeadOf<>(
                    2,
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                )
            ),
            new HasValues<>(
                "one",
                "two"
            )
        ).affirm();
    }

    @Test
    void returnsIntactIterator() {
        new Assertion<>(
            "Must return an intact iterator",
            new IterableOf<>(
                new HeadOf<>(
                    3,
                    new IteratorOf<>(
                        "one", "two"
                    )
                )
            ),
            new HasSize(2)
        ).affirm();
    }

    @Test
    void returnsEmptyIterator() {
        new Assertion<>(
            "Must throw an exception if empty",
            () -> new HeadOf<>(
                0,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void emptyIteratorForNegativeSize() {
        new Assertion<>(
            "Must throw an exception for negative size",
            () -> new HeadOf<>(
                -1,
                new IteratorOf<>(
                    "one", "two"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void iteratesForEachRemaining() {
        final List<String> lst = new ArrayList<>(2);
        new HeadOf<>(
            2,
            new IteratorOf<>(
                "one", "two", "three", "four"
            )
        ).forEachRemaining(
            lst::add
        );
        new Assertion<>(
            "Should iterate over 2 head elements",
            lst,
            new HasValues<>(
                "one",
                "two"
            )
        ).affirm();
    }

    @Test
    void removeNotSupported() {
        new Assertion<>(
            "Remove should not be supported",
            () -> {
                new HeadOf<>(
                    1,
                    new IteratorOf<>(
                        "one", "two", "three", "four"
                    )
                ).remove();
                return "Should have thrown exception";
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

}
