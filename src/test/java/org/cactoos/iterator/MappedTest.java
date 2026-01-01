/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link Mapped}.
 *
 * @since 0.47
 */
final class MappedTest {
    @Test
    void iteratatesOver() {
        new Assertion<>(
            "must map values of iterator",
            new IterableOf<>(
                new Mapped<>(
                    Number::toString,
                    new IteratorOf<Number>(1L, 2, 0)
                )
            ),
            new HasValues<>("1", "2", "0")
        ).affirm();
    }

    @Test
    void failsIfIteratorExhausted() {
        final Iterator<String> iterator = new Mapped<>(
            Number::toString,
            new IteratorOf<Number>(1)
        );
        iterator.next();
        new Assertion<>(
            "must throw NSEE",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
