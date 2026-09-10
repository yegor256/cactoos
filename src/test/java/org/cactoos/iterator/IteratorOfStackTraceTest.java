/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link StackTraceIterator}.
 * @since 0.56
 */
final class IteratorOfStackTraceTest {

    @Test
    void hasNextReturnsTrueForFirstCall() {
        MatcherAssert.assertThat(
            "First call 'hasNext' should return true",
            new IteratorOfStackTrace(
                new Throwable(new Throwable())
            ).hasNext(),
            new IsTrue()
        );
    }

    @Test
    void nextReturnsInnerException() {
        final Throwable inner = new Throwable();
        MatcherAssert.assertThat(
            "First call 'next' should return inner exception",
            new IteratorOfStackTrace(new Throwable(inner)).next(),
            new IsEqual<>(inner)
        );
    }

    @Test
    void hasNextReturnsFalseAfterTraversal() {
        final IteratorOfStackTrace iter = new IteratorOfStackTrace(
            new Throwable(new Throwable())
        );
        iter.next();
        MatcherAssert.assertThat(
            "Second call 'hasNext' should return false",
            iter.hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test
    void nextThrowsWhenExhausted() {
        final IteratorOfStackTrace iter = new IteratorOfStackTrace(
            new Throwable(new Throwable())
        );
        iter.next();
        MatcherAssert.assertThat(
            "Call 'next' should throw NoSuchElementException",
            iter::next,
            new Throws<Throwable>(NoSuchElementException.class)
        );
    }
}
