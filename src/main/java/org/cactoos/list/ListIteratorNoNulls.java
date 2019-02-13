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

import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicLong;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * A decorator of {@link ListIterator} that is immutable and tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.39
 */
public final class ListIteratorNoNulls<T> implements ListIterator<T> {

    /**
     * ListIterator.
     */
    private final ListIterator<T> listiterator;

    /**
     * Position.
     */
    private final AtomicLong pos;

    /**
     * Ctor.
     *
     * @param src List iterator.
     */
    public ListIteratorNoNulls(final ListIterator<T> src) {
        this.listiterator = new ListIteratorOf<>(src);
        this.pos = new AtomicLong();
    }

    @Override
    public boolean hasNext() {
        return this.listiterator.hasNext();
    }

    @Override
    public T next() {
        final T next = this.listiterator.next();
        if (next == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Next item #%d of #listiterator() is NULL",
                        this.pos.get()
                    )
                ).asString()
            );
        }
        this.pos.incrementAndGet();
        return next;
    }

    @Override
    public boolean hasPrevious() {
        return this.listiterator.hasPrevious();
    }

    @Override
    public T previous() {
        final T next = this.listiterator.previous();
        if (next == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Previous item #%d of #listiterator() is NULL",
                        this.pos.get()
                    )
                ).asString()
            );
        }
        this.pos.decrementAndGet();
        return next;
    }

    @Override
    public int nextIndex() {
        return this.listiterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.listiterator.previousIndex();
    }

    @Override
    public void remove() {
        this.listiterator.remove();
    }

    @Override
    public void set(final T item) {
        this.listiterator.set(item);
    }

    @Override
    public void add(final T item) {
        this.listiterator.add(item);
    }

}
