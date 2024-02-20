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

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Tests for {@link IteratorOfBytes}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IteratorOfBytesTest {

    @Test
    void canBeConstructedFromString() throws Exception {
        final Iterator<Byte> itr = new IteratorOfBytes(
            "F"
        );
        new Assertion<>(
            "Must have 1 element",
            new ListOf<Object>(
                itr.next(),
                itr.hasNext()
            ),
            new HasValues<>(
                (byte) 'F', false
            )
        ).affirm();
    }

    @Test
    void canBeConstructedFromText() throws Exception {
        final Iterator<Byte> itr = new IteratorOfBytes(
            new TextOf("ABC")
        );
        new Assertion<>(
            "Must have 3 elements",
            new ListOf<Object>(
                itr.next(),
                itr.next(),
                itr.next(),
                itr.hasNext()
            ),
            new HasValues<>(
                (byte) 'A', (byte) 'B', (byte) 'C', false
            )
        ).affirm();
    }

    @Test
    void emptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "hasNext is true for empty iterator.",
            new IteratorOfBytes().hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void emptyIteratorThrowsException() {
        Assertions.assertThrows(
            NoSuchElementException.class,
            () -> new IteratorOfBytes().next()
        );
    }

    @Test
    void nonEmptyIteratorDoesNotHaveNext() {
        new Assertion<>(
            "hasNext is true for fully traversed iterator.",
            this.iteratorWithFetchedElements().hasNext(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void nonEmptyIteratorThrowsException() {
        Assertions.assertThrows(
            NoSuchElementException.class,
            () -> this.iteratorWithFetchedElements().next()
        );
    }

    private IteratorOfBytes iteratorWithFetchedElements() {
        final IteratorOfBytes iterator =
            new IteratorOfBytes((byte) 1, (byte) 2, (byte) 3);
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
