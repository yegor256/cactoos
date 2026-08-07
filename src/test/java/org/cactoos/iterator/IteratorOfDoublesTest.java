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
 * Tests for {@link IteratorOfDoubles}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.34
 */
final class IteratorOfDoublesTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator.",
            new IteratorOfDoubles().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void emptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected on iterating empty doubles.",
            () -> new IteratorOfDoubles().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for fully traversed iterator.",
            this.iteratorWithFetchedElements().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        MatcherAssert.assertThat(
            "Exception is expected for fully traversed iterator.",
            () -> this.iteratorWithFetchedElements().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    private IteratorOfDoubles iteratorWithFetchedElements() {
        final IteratorOfDoubles iterator =
            new IteratorOfDoubles(1.1d, 2.2d, 3.3d);
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
