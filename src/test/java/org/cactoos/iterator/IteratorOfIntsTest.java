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
 * Tests for {@link IteratorOfInts}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfIntsTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator.",
            new IteratorOfInts().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected on iterating empty ints.",
            () -> new IteratorOfInts().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfInts iterator = new IteratorOfInts(1, 2);
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
        final IteratorOfInts iterator = new IteratorOfInts(1);
        iterator.next();
        MatcherAssert.assertThat(
            "Exception is expected for fully traversed iterator.",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        );
    }
}
