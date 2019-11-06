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

import java.util.List;
import java.util.ListIterator;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Iterator of the list that doesn't allow mutations.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 0.35
 */
public final class ListIteratorOf<T> implements ListIterator<T> {

    /**
     * Original list iterator.
     */
    private final Unchecked<ListIterator<T>> origin;

    /**
     * Ctor.
     * @param list List that will be called to get a list iterator.
     */
    public ListIteratorOf(final List<T> list) {
        this(list::listIterator);
    }

    /**
     * Ctor.
     * @param list List that will be called to get a list iterator.
     * @param index Start index for a newly created list iterator.
     */
    public ListIteratorOf(final List<T> list, final int index) {
        this(() -> list.listIterator(index));
    }

    /**
     * Ctor.
     * @param iter Original list iterator.
     */
    public ListIteratorOf(final ListIterator<T> iter) {
        this(() -> iter);
    }

    /**
     * Ctor.
     * @param orig Original list iterator.
     */
    public ListIteratorOf(final Scalar<ListIterator<T>> orig) {
        this.origin = new Unchecked<>(new Sticky<>(orig));
    }

    @Override
    public boolean hasNext() {
        return this.origin.value().hasNext();
    }

    @Override
    public T next() {
        return this.origin.value().next();
    }

    @Override
    public boolean hasPrevious() {
        return this.origin.value().hasPrevious();
    }

    @Override
    public T previous() {
        return this.origin.value().previous();
    }

    @Override
    public int nextIndex() {
        return this.origin.value().nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.origin.value().previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "Iterator is read-only and doesn't allow removing items"
        );
    }

    @Override
    public void set(final T item) {
        throw new UnsupportedOperationException(
            "Iterator is read-only and doesn't allow rewriting items"
        );
    }

    @Override
    public void add(final T item) {
        throw new UnsupportedOperationException(
            "Iterator is read-only and doesn't allow adding items"
        );
    }
}
