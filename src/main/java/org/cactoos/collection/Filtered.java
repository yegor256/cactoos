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
package org.cactoos.collection;

import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;

/**
 * Filtered collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of source item
 * @since 1.16
 */
public final class Filtered<X> extends CollectionEnvelope<X> {

    /**
     * Ctor.
     * @param src Source collection
     * @param func Filter function
     * @since 0.23
     */
    @SafeVarargs
    public Filtered(final Func<X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param func Filter function
     * @since 0.23
     */
    public Filtered(final Func<X, Boolean> func, final Iterator<X> src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param func Filter function
     */
    public Filtered(final Func<X, Boolean> func, final Iterable<X> src) {
        super(() -> new CollectionOf<>(
            new org.cactoos.iterable.Filtered<>(
                func, src
            )
        ));
    }

}
