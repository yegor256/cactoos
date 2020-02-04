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
package org.cactoos.collection;

import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;

/**
 * Mapped collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.14
 */
public final class Mapped<X, Y> extends CollectionEnvelope<Y> {

    /**
     * Ctor.
     * @param fnc Func
     * @param src Source collection
     * @since 0.23
     */
    @SafeVarargs
    public Mapped(final Func<X, Y> fnc, final X... src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Func
     * @param src Source collection
     */
    public Mapped(final Func<X, Y> fnc, final Iterable<X> src) {
        super(
            new Sticky<>(
                new org.cactoos.iterable.Mapped<>(fnc, src)
            )
        );
    }
}
