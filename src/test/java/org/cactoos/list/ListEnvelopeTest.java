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
package org.cactoos.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link ListEnvelope}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class ListEnvelopeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedRemove() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("one");
                return inner;
            }
        ) {
        };
        final Iterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedSet() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("three");
                return inner;
            }
        ) {
        };
        final ListIterator<String> iterator = list.listIterator(1);
        iterator.set("zero");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedAdd() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("ten");
                return inner;
            }
        ) {
        };
        final ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.add("twenty");
    }

    @Test
    public void getsPreviousIndex() {
        MatcherAssert.assertThat(
            "List iterator returns incorrect previous index",
            new org.cactoos.list.ListIterator<>(
                new ListOf<>(1)
            ).previousIndex(),
            new IsEqual<>(-1)
        );
    }

    @Test
    public void getsPrevious() {
        MatcherAssert.assertThat(
            "List iterator returns incorrect previous item",
            new org.cactoos.list.ListIterator<>(
                new ListOf<>(3, 7),
                1
            ).previous(),
            new IsEqual<>(3)
        );
    }

    @Test
    public void getsNextIndex() {
        MatcherAssert.assertThat(
            "List iterator returns incorrect next index",
            new org.cactoos.list.ListIterator<>(
                new ListOf<>(1)
            ).nextIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    public void getsNext() {
        MatcherAssert.assertThat(
            "List iterator returns incorrect next item",
            new org.cactoos.list.ListIterator<>(
                new ListOf<>(5, 11, 13),
                1
            ).next(),
            new IsEqual<>(11)
        );
    }
}
