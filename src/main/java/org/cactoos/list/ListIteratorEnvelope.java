/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
 * {@link ListIterator} envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 0.47
 */
public abstract class ListIteratorEnvelope<T> implements ListIterator<T> {

    /**
     * Original list iterator.
     */
    private final ListIterator<T> origin;

    /**
     * Ctor.
     * @param iter Original list iterator.
     */
    public ListIteratorEnvelope(final ListIterator<T> iter) {
        this.origin = iter;
    }

    @Override
    public final boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public final T next() {
        return this.origin.next();
    }

    @Override
    public final boolean hasPrevious() {
        return this.origin.hasPrevious();
    }

    @Override
    public final T previous() {
        return this.origin.previous();
    }

    @Override
    public final int nextIndex() {
        return this.origin.nextIndex();
    }

    @Override
    public final int previousIndex() {
        return this.origin.previousIndex();
    }

    @Override
    public final void remove() {
        this.origin.remove();
    }

    @Override
    public final void set(final T item) {
        this.origin.set(item);
    }

    @Override
    public final void add(final T item) {
        this.origin.add(item);
    }
}
