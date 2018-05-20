/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Tests for {@link IteratorOfBytes}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.34
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IteratorOfBytesTest {
    @Test
    public void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for empty iterator.",
            new IteratorOfBytes().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorThrowsException() {
        new IteratorOfBytes().next();
    }

    @Test
    public void nonEmptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "hasNext is true for fully traversed iterator.",
            this.iteratorWithFetchedElements().hasNext(),
            new IsEqual<>(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void nonEmptyIteratorThrowsException() {
        this.iteratorWithFetchedElements().next();
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
