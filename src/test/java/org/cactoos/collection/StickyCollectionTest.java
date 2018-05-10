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
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyCollection}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StickyCollectionTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new StickyCollection<>(new ListOf<Integer>(1, 2, 0, -1)),
            new BehavesAsCollection<>(0)
        );
    }

    @Test
    public void ignoresChangesInIterable() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Collection<Integer> list = new StickyCollection<>(
            new ListOf<>(
                () -> Collections.nCopies(size.incrementAndGet(), 0).iterator()
            )
        );
        MatcherAssert.assertThat(
            "Can't ignore the changes in the underlying iterable",
            list.size(),
            Matchers.equalTo(list.size())
        );
    }

    @Test
    public void decoratesArray() throws Exception {
        MatcherAssert.assertThat(
            "Can't decorate an array of numbers",
            new StickyCollection<>(-1, 0).size(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void testEmpty() {
        MatcherAssert.assertThat(
            new StickyCollection<>().isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void testContains() {
        MatcherAssert.assertThat(
            new StickyCollection<>(1, 2).contains(1),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void testToArray() {
        MatcherAssert.assertThat(
            new StickyCollection<>(1, 2).toArray(),
            Matchers.arrayContaining(1, 2)
        );
    }

    @Test
    public void testToArrayIntoArray() {
        final Integer[] arr = new Integer[2];
        MatcherAssert.assertThat(
            new StickyCollection<>(1, 2).toArray(arr),
            Matchers.arrayContaining(1, 2)
        );
    }

    @Test
    public void testContainsAll() {
        MatcherAssert.assertThat(
            new StickyCollection<>(1, 2).containsAll(new ListOf<>(1, 2)),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new StickyCollection<>(1, 2).add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new StickyCollection<>(1, 2).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new StickyCollection<>(1, 2).addAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new StickyCollection<>(1, 2).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new StickyCollection<>(1, 2).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new StickyCollection<>(1, 2).clear();
    }

}
