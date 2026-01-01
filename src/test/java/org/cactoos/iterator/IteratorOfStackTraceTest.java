/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "First call 'hasNext' should return true.",
            iter.hasNext(),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "First call 'next' should return inner exception.",
            iter.next(),
            new IsEqual<>(inner)
        ).affirm();
        new Assertion<>(
            "Second call 'hasNext' should return false.",
            iter.hasNext(),
            new IsEqual<>(false)
        ).affirm();
        new Assertion<>(
            "Third call 'next' should throw NSEE.",
            () -> iter.next(),
            new Throws<Throwable>(NoSuchElementException.class)
        ).affirm();
    }
}
