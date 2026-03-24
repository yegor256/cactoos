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
 *
 * @since 0.56
 */
final class IteratorOfStackTraceTest {
    @Test
    void iteratorOfStackTraceTest() {
        final Throwable inner = new Throwable();
        final IteratorOfStackTrace iter =  new IteratorOfStackTrace(new Throwable(inner));
        MatcherAssert.assertThat(
            "First call 'hasNext' should return true.",
            iter.hasNext(),
            new IsTrue()
        );
        MatcherAssert.assertThat(
            "First call 'next' should return inner exception.",
            iter.next(),
            new IsEqual<>(inner)
        );
        MatcherAssert.assertThat(
            "Second call 'hasNext' should return false.",
            iter.hasNext(),
            new IsEqual<>(false)
        );
        MatcherAssert.assertThat(
            "Third call 'next' should throw NSEE.",
            () -> iter.next(),
            new Throws<Throwable>(NoSuchElementException.class)
        );
    }
}
