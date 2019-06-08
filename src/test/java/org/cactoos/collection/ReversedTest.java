/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link org.cactoos.collection.Reversed}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReversedTest {

    @Test
    public void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a collection",
            new NoNulls<>(
                new Reversed<>(
                    new IterableOf<Integer>(0, -1, 2)
                )
            ),
            new BehavesAsCollection<>(0)
        ).affirm();
    }

    @Test
    public void reverseList() {
        final String last = "last";
        new Assertion<>(
            "Must reverse list",
            new Reversed<>(
                new IterableOf<>(
                    "item", last
                )
            ).iterator().next(),
            new IsEqual<>(last)
        ).affirm();
    }

    @Test
    public void reverseEmptyList() {
        new Assertion<>(
            "Must reverse empty list",
            new Reversed<>(
                new ListOf<>()
            ),
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    public void size() {
        new Assertion<>(
            "Size must be the same",
            new Reversed<>(
                new IterableOf<>(
                    "0", "1", "2"
                )
            ).size(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    public void isEmpty() {
        new Assertion<>(
            "Must be not empty",
            new Reversed<>(
                new IterableOf<>(
                    6, 16
                )
            ).isEmpty(),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    public void contains() {
        final String word = "objects";
        new Assertion<>(
            "Must contain element",
            new Reversed<>(
                new IterableOf<>(
                    "hello", "elegant", word
                )
            ).contains(word),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).add(6);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).addAll(new ArrayList<>(6));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        new Reversed<>(
            new IterableOf<>(
                1, 2, 3, 4
            )
        ).clear();
    }

    @Test
    public void toArray() {
        new Assertion<>(
            "Array must contain element",
            new Reversed<>(
                new IterableOf<>(
                    1, 2, 3, 4
                )
            ).toArray(),
            new AllOf<>(
                new IterableOf<org.hamcrest.Matcher<? super Object[]>>(
                    new IsArrayContaining<>(new IsEqual<>(4)),
                    new IsArrayContaining<>(new IsEqual<>(3)),
                    new IsArrayContaining<>(new IsEqual<>(2)),
                    new IsArrayContaining<>(new IsEqual<>(1))
                )
            )
        ).affirm();
    }

    @Test
    public void toArrayWithArray() {
        new Assertion<>(
            "Array for Reversed must contain elements",
            new Reversed<>(
                new IterableOf<>(
                    1, 2, 3, 4, 5
                )
            ).toArray(new Integer[]{5, 6}),
            new AllOf<>(
                new IterableOf<org.hamcrest.Matcher<? super Integer[]>>(
                    new IsArrayContaining<>(new IsEqual<>(5)),
                    new IsArrayContaining<>(new IsEqual<>(4)),
                    new IsArrayContaining<>(new IsEqual<>(3)),
                    new IsArrayContaining<>(new IsEqual<>(2)),
                    new IsArrayContaining<>(new IsEqual<>(1))
                )
            )
        ).affirm();
    }

    @Test
    public void containsAll() {
        final String first = "first";
        final String second = "second";
        new Assertion<>(
            "Must contains all elements",
            new Reversed<>(
                new IterableOf<>(
                    first, second, "third"
                )
            ).containsAll(new ListOf<>(first, second)),
            new IsEqual<>(true)
        ).affirm();
    }
}
