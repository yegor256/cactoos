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
package org.cactoos.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ListEnvelope}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals" })
public final class ListEnvelopeTest {

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedRemove() {
        final ListEnvelope<String> list = new StringList("one");
        final Iterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedSet() {
        final ListEnvelope<String> list = new StringList("one");
        final ListIterator<String> iterator = list.listIterator(1);
        iterator.set("zero");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedAdd() {
        final ListEnvelope<String> list = new StringList("one");
        final ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.add("two");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void subListReturnsListIteratorWithUnsupportedRemove() {
        final ListEnvelope<String> list = new StringList("one");
        final Iterator<String> iterator = list.subList(0, 1)
            .listIterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void subListReturnsListIteratorWithUnsupportedSet() {
        final ListEnvelope<String> list = new StringList("one");
        final ListIterator<String> iterator = list.subList(0, 1)
            .listIterator(1);
        iterator.set("zero");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void subListReturnsListIteratorWithUnsupportedAdd() {
        final ListEnvelope<String> list = new StringList("one");
        final ListIterator<String> iterator = list.subList(0, 1)
            .listIterator();
        iterator.next();
        iterator.add("two");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeIsNotSupported() {
        final ListEnvelope<String> list = new StringList("one");
        list.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIsNotSupported() {
        final ListEnvelope<String> list = new StringList("one");
        list.set(0, "zero");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addIsNotSupported() {
        final ListEnvelope<String> list = new StringList("one");
        list.add("two");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsSubListWithUnsupportedRemove() {
        final ListEnvelope<String> list = new StringList("one");
        list.subList(0, 1).remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsSubListWithUnsupportedSet() {
        final ListEnvelope<String> list = new StringList("one");
        list.subList(0, 1).set(0, "zero");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsSubListWithUnsupportedAdd() {
        final ListEnvelope<String> list = new StringList("one");
        list.subList(0, 1).add("two");
    }

    @Test
    public void getsPreviousIndex() {
        new Assertion<>(
            "List iterator returns incorrect previous index",
            new ListIteratorOf<>(
                new ListOf<>(1)
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    public void getsPrevious() {
        new Assertion<>(
            "List iterator returns incorrect previous item",
            new ListIteratorOf<>(
                new ListOf<>(3, 7),
                1
            ).previous(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    public void getsNextIndex() {
        new Assertion<>(
            "List iterator returns incorrect next index",
            new ListIteratorOf<>(
                new ListOf<>(1)
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void getsNext() {
        new Assertion<>(
            "List iterator returns incorrect next item",
            new ListIteratorOf<>(
                new ListOf<>(5, 11, 13),
                1
            ).next(),
            new IsEqual<>(11)
        ).affirm();
    }

    private static final class StringList extends ListEnvelope<String> {
        StringList(final String... elements) {
            super(() -> Arrays.asList(elements));
        }
    }
}
