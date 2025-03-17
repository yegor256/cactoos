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
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Paged iterator.
 * Elements will continue to be provided so long as {@code next} produces
 * non-empty iterators.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.49
 */
public final class Paged<X> implements Iterator<X> {

    /**
     * Current element.
     */
    private final AtomicReference<Iterator<? extends X>> current;

    /**
     * Function to get the next element.
     */
    private final Func<? super Iterator<? extends X>, ? extends Iterator<? extends X>> subsequent;

    /**
     * Ctor.
     * @param first First element.
     * @param next Function to get the next element.
     */
    public Paged(
        final Iterator<? extends X> first,
        final Func<? super Iterator<? extends X>, ? extends Iterator<? extends X>> next
    ) {
        this.current = new AtomicReference<>(first);
        this.subsequent = next;
    }

    @Override
    public boolean hasNext() {
        if (!this.current.get().hasNext()) {
            final Iterator<? extends X> next = new UncheckedFunc<>(this.subsequent).apply(
                this.current.get()
            );
            this.current.set(next);
        }
        return this.current.get().hasNext();
    }

    @Override
    public X next() {
        if (this.hasNext()) {
            return this.current.get().next();
        }
        throw new NoSuchElementException();
    }
}
