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
 * Tests for {@link IteratorOfLongs}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfLongsTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator.",
            new IteratorOfLongs().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected for empty iterator of longs.",
            () -> new IteratorOfLongs().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfLongs iterator = new IteratorOfLongs(1L, 2L);
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
        final IteratorOfLongs iterator = new IteratorOfLongs(1L);
        iterator.next();
        MatcherAssert.assertThat(
            "Exception is expected for fully traversed iterator.",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }
}
