/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Joined}.
 * @since 0.14
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class JoinedTest {

    @Test
    @SuppressWarnings("unchecked")
    void joinsIterators() {
        MatcherAssert.assertThat(
            "Must concatenate iterable of iterators together",
            new IterableOf<>(
                new Joined<>(
                    new IterableOf<>(
                        new IteratorOf<>("x"),
                        new IteratorOf<>("y")
                    )
                )
            ),
            new IsEqual<>(new IterableOf<>("x", "y"))
        );
    }

    @Test
    void callsNextDirectlyOnNonEmptyIterator() {
        MatcherAssert.assertThat(
            "Must call next method directly on non-empty iterator",
            new Joined<Integer>(
                new IteratorOf<>(1),
                new IteratorOf<>(2)
            ).next(),
            new IsEqual<>(1)
        );
    }

    @Test
    void throwsExceptionWhenCallNextOnEmptyIterator() {
        MatcherAssert.assertThat(
            "Must throw an exception",
            () -> new Joined<Integer>(new IteratorOf<>()).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinItemAndIterable() {
        MatcherAssert.assertThat(
            "Must join item and iterable",
            new IterableOf<>(
                new Joined<>(
                    0,
                    new IteratorOf<>(1, 2, 3)
                )
            ),
            new IsEqual<>(new IterableOf<>(0, 1, 2, 3))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinIterableAndItem() {
        MatcherAssert.assertThat(
            "Must join iterable and item",
            new IterableOf<>(
                new Joined<>(
                    new IteratorOf<>(1, 2, 3),
                    0
                )
            ),
            new IsEqual<>(new IterableOf<>(1, 2, 3, 0))
        );
    }
}
