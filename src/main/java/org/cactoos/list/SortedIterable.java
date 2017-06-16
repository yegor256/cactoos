/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.util.Comparator;
import java.util.Iterator;

/**
 * Sorted iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.7
 */
public final class
    SortedIterable<T extends Comparable<? super T>> implements
    Iterable<T> {

    /**
     * Decorated iterable.
     */
    private final Iterable<T> iterable;

    /**
     * Comparator.
     */
    private final Comparator<T> comparator;

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public SortedIterable(final T... src) {
        this(new ArrayAsIterable<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    public SortedIterable(final Iterable<T> src) {
        this(Comparator.naturalOrder(), src);
    }

    /**
     * Ctor.
     * @param src The underlying iterable
     * @param cmp The comparator
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public SortedIterable(final Comparator<T> cmp, final T... src) {
        this(cmp, new ArrayAsIterable<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying iterable
     * @param cmp The comparator
     */
    public SortedIterable(final Comparator<T> cmp, final Iterable<T> src) {
        this.iterable = src;
        this.comparator = cmp;
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedIterator<>(this.comparator, this.iterable.iterator());
    }
}
