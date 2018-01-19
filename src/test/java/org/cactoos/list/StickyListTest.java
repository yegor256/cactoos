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
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyList}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StickyListTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new StickyList<>(1, 0, -1, -1, 2),
            new BehavesAsList<>(0)
        );
    }

    @Test
    public void ignoresChangesInIterable() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final List<Integer> list = new StickyList<>(
            new ListOf<Integer>(
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
            new StickyList<>(-1, 0).size(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void testEmpty() {
        MatcherAssert.assertThat(
            new StickyList<>().isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void testContains() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).contains(1),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void testToArray() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).toArray(),
            Matchers.arrayContaining(1, 2)
        );
    }

    @Test
    public void testToArrayIntoArray() {
        final Integer[] arr = new Integer[2];
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).toArray(arr),
            Matchers.arrayContaining(1, 2)
        );
    }

    @Test
    public void testContainsAll() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).containsAll(Arrays.asList(1, 2)),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void testIndexOf() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).indexOf(1),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void testLastIndexOf() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2, 2).lastIndexOf(2),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void testGet() {
        MatcherAssert.assertThat(
            new StickyList<>(1, 2).get(1),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void testSubList() {
        final List<Integer> list = new StickyList<>(
            1, 2, 0, -1
        ).subList(0, 2);
        MatcherAssert.assertThat(
            list.size(),
            Matchers.equalTo(2)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new StickyList<>(1, 2).add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new StickyList<>(1, 2).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new StickyList<>(1, 2).addAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new StickyList<>(1, 2).removeAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new StickyList<>(1, 2).retainAll(new ArrayList<>(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new StickyList<>(1, 2).clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() throws Exception {
        new StickyList<>(1, 2).set(1, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddIndex() throws Exception {
        new StickyList<>(1, 2).add(1, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveIndex() throws Exception {
        new StickyList<>(1, 2).remove(1);
    }

    @Test
    public void makesListFromMappedIterable() throws Exception {
        final List<Integer> list = new StickyList<>(
            new Mapped<>(
                i -> i + 1,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list",
            list, Matchers.iterableWithSize(4)
        );
        MatcherAssert.assertThat(
            "Can't turn a mapped iterable into a list, again",
            list, Matchers.iterableWithSize(4)
        );
    }

    @Test
    public void mapsToSameObjects() throws Exception {
        final List<Scalar<Integer>> list = new StickyList<>(
            new Mapped<>(
                i -> (Scalar<Integer>) () -> i,
                new IterableOf<>(1, -1, 0, 1)
            )
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            list.get(0), Matchers.equalTo(list.get(0))
        );
    }

}
