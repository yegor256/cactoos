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
package org.cactoos.collection;

import java.util.ArrayList;
import java.util.Collections;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.collection.Reversed}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReversedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new CollectionNoNulls<>(
                new Reversed<>(
                    new IterableOf<Integer>(0, -1, 2)
                )
            ),
            new BehavesAsCollection<>(0)
        );
    }

    @Test
    public void reverseList() throws Exception {
        final String last = "last";
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    "item", last
                )
            ).iterator().next(),
            Matchers.equalTo(last)
        );
    }

    @Test
    public void reverseEmptyList() throws Exception {
        MatcherAssert.assertThat(
            new Reversed<>(
                Collections.emptyList()
            ),
            Matchers.emptyIterable()
        );
    }

    @Test
    public void size() throws Exception {
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    "0", "1", "2"
                )
            ).size(),
            Matchers.equalTo(3)
        );
    }

    @Test
    public void isEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    6, 16
                )
            ).isEmpty(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void contains() throws Exception {
        final String word = "objects";
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    "hello", "elegant", word
                )
            ).contains(word),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).add(6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).addAll(new ArrayList<>(6));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).clear();
    }

    @Test
    public void toArray() throws Exception {
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    1, 2, 3, 4
                )
            ).toArray(),
            Matchers.arrayContaining(4, 3, 2, 1)
        );
    }

    @Test
    public void toArrayWithArray() throws Exception {
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    1, 2, 3, 4, 5
                )
            ).toArray(new Integer[]{5, 6}),
            Matchers.arrayContaining(5, 4, 3, 2, 1)
        );
    }

    @Test
    public void containsAll() throws Exception {
        final String first = "first";
        final String second = "second";
        MatcherAssert.assertThat(
            new Reversed<>(
                new IterableOf<>(
                    first, second, "third"
                )
            ).containsAll(new ListOf<>(first, second)),
            Matchers.equalTo(true)
        );
    }
}
