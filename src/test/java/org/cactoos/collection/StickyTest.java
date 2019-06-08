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
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sticky}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StickyTest {

    @Test
    public void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a collection",
            new Sticky<>(new ListOf<>(1, 2, 0, -1)),
            new BehavesAsCollection<>(0)
        ).affirm();
    }

    @Test
    public void ignoresChangesInIterable() {
        final AtomicInteger size = new AtomicInteger(2);
        final Collection<Integer> list = new Sticky<>(
            new ListOf<>(
                Collections.nCopies(size.incrementAndGet(), 0).iterator()
            )
        );
        new Assertion<>(
            "Can't ignore the changes in the underlying iterable",
            list.size(),
            new IsEqual<>(list.size())
        ).affirm();
    }

    @Test
    public void decoratesArray() {
        new Assertion<>(
            "Can't decorate an array of numbers",
            new Sticky<>(-1, 0),
            new IsCollectionWithSize<>(new IsEqual<>(2))
        ).affirm();
    }

    @Test
    public void testEmpty() {
        new Assertion<>(
            "Must be empty",
            new Sticky<>(),
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    public void testContains() {
        new Assertion<>(
            "Must contain element",
            new Sticky<>(1, 2).contains(1),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    public void testToArray() {
        new Assertion<>(
            "Array must contain elements",
            new Sticky<>(1, 2).toArray(),
            new AllOf<>(
                new IterableOf<org.hamcrest.Matcher<? super Object[]>>(
                    new IsArrayContaining<>(new IsEqual<>(1)),
                    new IsArrayContaining<>(new IsEqual<>(2))
                )
            )
        ).affirm();
    }

    @Test
    public void testToArrayIntoArray() {
        final Integer[] arr = new Integer[2];
        new Assertion<>(
            "Array from Sticky must contain elements",
            new Sticky<>(1, 2).toArray(arr),
            new AllOf<>(
                new IterableOf<org.hamcrest.Matcher<? super Integer[]>>(
                    new IsArrayContaining<>(new IsEqual<>(1)),
                    new IsArrayContaining<>(new IsEqual<>(2))
                )
            )
        ).affirm();
    }

    @Test
    public void testContainsAll() {
        new Assertion<>(
            "Must contains all",
            new Sticky<>(1, 2).containsAll(new ListOf<>(1, 2)),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        new Sticky<>(1, 2).add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        new Sticky<>(1, 2).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        new Sticky<>(1, 2).addAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        new Sticky<>(1, 2).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        new Sticky<>(1, 2).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        new Sticky<>(1, 2).clear();
    }
}
