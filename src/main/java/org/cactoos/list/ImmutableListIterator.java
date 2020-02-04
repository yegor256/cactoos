/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import java.util.ListIterator;

/**
 * Immutable {@link ListIterator} that doesn't allow mutations.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 1.0
 */
public final class ImmutableListIterator<T> implements ListIterator<T> {

    /**
     * Original list iterator.
     */
    private final ListIterator<T> origin;

    /**
     * Ctor.
     * @param iter Original list iterator.
     */
    public ImmutableListIterator(final ListIterator<T> iter) {
        this.origin = iter;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public T next() {
        return this.origin.next();
    }

    @Override
    public boolean hasPrevious() {
        return this.origin.hasPrevious();
    }

    @Override
    public T previous() {
        return this.origin.previous();
    }

    @Override
    public int nextIndex() {
        return this.origin.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.origin.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow removing items"
        );
    }

    @Override
    public void set(final T item) {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow rewriting items"
        );
    }

    @Override
    public void add(final T item) {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow adding items"
        );
    }
}
