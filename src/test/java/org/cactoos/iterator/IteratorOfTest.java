/**
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
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link IteratorOf}.
 *
 * @author Marat Reymers (marat.maratori@gmail.com)
 * @version $Id$
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IteratorOfTest {

    @Test
    public void emptyIteratorDoesNotHaveNext() {
        MatcherAssert.assertThat(
            "Can't create empty iterator",
            new IteratorOf<>().hasNext(),
            CoreMatchers.equalTo(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyIteratorThrowsException() {
        new IteratorOf<>().next();
    }

    @Test
    public void nonEmptyIteratorDoesNotHaveNext() {
        final IteratorOf<Integer> iterator = this.iteratorWithFetchedElements();
        MatcherAssert.assertThat(
            "Can't create non empty iterator",
            iterator.hasNext(),
            CoreMatchers.equalTo(false)
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void nonEmptyIteratorThrowsException() {
        final IteratorOf<Integer> iterator = this.iteratorWithFetchedElements();
        iterator.next();
    }

    @Test
    public void convertStringsToIterator() {
        MatcherAssert.assertThat(
            "Can't create an iterator of strings",
            () -> new IteratorOf<>(
                "a", "b", "c"
            ),
            Matchers.contains(
                "a", "b", "c"
            )
        );
    }

    private IteratorOf<Integer> iteratorWithFetchedElements() {
        final IteratorOf<Integer> iterator = new IteratorOf<>(
            1, 2, 3
        );
        iterator.next();
        iterator.next();
        iterator.next();
        return iterator;
    }
}
