/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link IteratorOfShorts}.
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidUsingShortType")
final class IteratorOfShortsTest {

    @Test
    void emptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "hasNext is true for empty iterator.",
            new IteratorOfShorts().hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void emptyIteratorThrowsException() {
        new Assertion<>(
            "Exception is expected for empty iterator",
            () -> new IteratorOfShorts().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOfShorts iterator = new IteratorOfShorts(
            (short) 1, (short) 2
        );
        iterator.next();
        iterator.next();
        new Assertion<>(
            "hasNext is true for fully traversed iterator.",
            iterator.hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        final IteratorOfShorts iterator = new IteratorOfShorts((short) 1);
        iterator.next();
        new Assertion<>(
            "Exception is not thrown after last item iteration",
            iterator::next,
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
