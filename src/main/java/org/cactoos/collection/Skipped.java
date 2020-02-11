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

import org.cactoos.iterable.IterableOf;

/**
 * Skipped collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of source item
 * @since 0.34
 * @todo #1242:30min Remove this class and replace it everywhere
 *  it was needed by the appropriate usage of Skipped from iterable
 *  (composed with ListOf or SetOf in case a copy is needed)
 *  or any other relevant concrete collection implementation.
 *  See #1242 for the rationale about this.
 */
public final class Skipped<T> extends CollectionEnvelope<T> {

    /**
     * Ctor.
     * @param skip How many to skip
     * @param src Source elements
     */
    @SafeVarargs
    public Skipped(final int skip, final T... src) {
        this(skip, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param skip How many to skip
     * @param src Source iterable
     */
    public Skipped(final int skip, final Iterable<T> src) {
        super(new Sticky<>(
            new org.cactoos.iterable.Skipped<>(skip, src)
        ));
    }
}
