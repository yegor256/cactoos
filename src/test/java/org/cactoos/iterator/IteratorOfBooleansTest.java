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
 * Tests for {@link IteratorOfBooleans}.
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfBooleansTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator",
            new IteratorOfBooleans().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected for empty iterator",
            () -> new IteratorOfBooleans().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfBooleans iterator = new IteratorOfBooleans(true, false);
        iterator.next();
        iterator.next();
        MatcherAssert.assertThat(
            "hasNext is true for already traversed iterator",
            iterator.hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final IteratorOfBooleans iterator = new IteratorOfBooleans(true);
        iterator.next();
        MatcherAssert.assertThat(
            "Exception is expected after iterating last item",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }
}
