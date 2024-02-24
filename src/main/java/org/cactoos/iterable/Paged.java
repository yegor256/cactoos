/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
 */
public final class Paged<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * <p>
     * @param first First bag of elements
     * @param next Subsequent bags of elements
     */
    public Paged(
        final Iterable<? extends X> first,
        final Func<? super Iterable<? extends X>, ? extends Iterable<? extends X>> next
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Paged<X>(
                    first.iterator(),
                    page -> next.apply(new IterableOf<>(page)).iterator()
                )
            )
        );
    }
}
