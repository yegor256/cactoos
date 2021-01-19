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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.Func;

/**
 * Paged iterable.
 * Elements will continue to be provided so long as {@code next} produces
 * non-empty iterators.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.47
 * @todo #1464:30min Replace the constructor so that instead it takes a {@link Iterable}
 *  as first and a {@code Func<Iterable, Iterable>} as next in order to homogenise it with
 *  the other {@link Iterable} implementations.
 */
public final class Paged<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * <p>
     * @param first First bag of elements
     * @param next Subsequent bags of elements
     * @param <I> Custom iterator
     */
    public <I extends Iterator<X>> Paged(
        final I first, final Func<I, I> next
    ) {
        super(
            new IterableOf<>(
                new org.cactoos.iterator.Paged<>(first, next)
            )
        );
    }
}
