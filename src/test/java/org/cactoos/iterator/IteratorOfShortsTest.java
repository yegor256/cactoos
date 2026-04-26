/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link IteratorOfShorts}.
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfShortsTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator.",
            new IteratorOfShorts().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected for empty iterator",
            () -> new IteratorOfShorts().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfShorts iterator = new IteratorOfShorts(
            (short) 1, (short) 2
        );
        iterator.next();
        iterator.next();
        MatcherAssert.assertThat(
            "hasNext is true for fully traversed iterator.",
            iterator.hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final IteratorOfShorts iterator = new IteratorOfShorts((short) 1);
        iterator.next();
        MatcherAssert.assertThat(
            "Exception is not thrown after last item iteration",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }
}
