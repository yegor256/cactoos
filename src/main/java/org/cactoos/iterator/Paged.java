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
 * @param <I> Subtype of item iterator
 * @param <X> Type of item
 * @since 0.49
 * @todo #1464:30min We want to use {@code Iterator<X>} directly in the class without
 *  introducing I, maybe using {@code Iterator<? extends X>} for example because, we don't
 *  have to constrain the {@link Func} to give exactly the same Iterator type as long as it
 *  is an iterator that provides X. {@link org.cactoos.iterable.Paged} should be changed too.
 */
public final class Paged<I extends Iterator<X>, X> implements Iterator<X> {

    /**
     * Current element.
     */
    private final AtomicReference<I> current;

    /**
     * Function to get the next element.
     */
    private final Func<I, I> subsequent;

    /**
     * Ctor.
     * @param first First element.
     * @param next Function to get the next element.
     */
    public Paged(final I first, final Func<I, I> next) {
        this.current = new AtomicReference<>(first);
        this.subsequent = next;
    }

    @Override
    public boolean hasNext() {
        if (!this.current.get().hasNext()) {
            final I next = new UncheckedFunc<>(this.subsequent).apply(
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
