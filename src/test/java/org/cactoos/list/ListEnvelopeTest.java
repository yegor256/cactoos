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
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test case for {@link ListEnvelope}.
 *
 * @since 0.32
 * @todo #814:30min Implement immutable ListIterator that should
 *  be returned from ListEnvelope when calling `listIterator` method. After
 *  it is implemented remove `@Ignore` from the tests below - all of them
 *  should pass after the change.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ListEnvelopeTest {

    @Ignore
    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedRemove() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("one");
                return inner;
            }
        ) { };
        final Iterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.remove();
    }

    @Ignore
    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedSet() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("three");
                return inner;
            }
        ) { };
        final ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.set("zero");
    }

    @Ignore
    @Test(expected = UnsupportedOperationException.class)
    public void returnsListIteratorWithUnsupportedAdd() {
        final ListEnvelope<String> list = new ListEnvelope<String>(
            () -> {
                final List<String> inner = new LinkedList<>();
                inner.add("ten");
                return inner;
            }
        ) { };
        final ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.add("twenty");
    }
}
