/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IteratorOf}.
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "Must create empty iterator",
            new IteratorOf<>().hasNext(),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Must throw an exception if empty",
            () -> new IteratorOf<>().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final Iterator<Integer> iterator = new IteratorOf<>(
            1, 2, 3
        );
        while (iterator.hasNext()) {
            iterator.next();
        }
        MatcherAssert.assertThat(
            "Must create non empty iterator",
            iterator.hasNext(),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final Iterator<Character> iterator = new IteratorOf<>(
            'a', 'b'
        );
        while (iterator.hasNext()) {
            iterator.next();
        }
        MatcherAssert.assertThat(
            "Must throw an exception if consumed",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void convertStringsToIterator() {
        MatcherAssert.assertThat(
            "Must create an iterator of strings",
            new IterableOf<>(
                new IteratorOf<>(
                    "a", "b", "c"
                )
            ),
            new HasValues<>(
                "a", "b", "c"
            )
        );
    }
}
