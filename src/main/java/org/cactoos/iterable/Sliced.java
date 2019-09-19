/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

/**
 * Sliced portion of the iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 1.0.0
 */
public final class Sliced<T> extends IterableEnvelope<T>  {

    /**
     * Ctor.
     * @param index Starting index
     * @param limit Maximum number of elements for resulted iterator
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Sliced(final int index, final int limit, final T... src) {
        this(index, limit, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param index Starting index
     * @param limit Maximum number of elements for resulted iterator
     * @param iterable Decorated iterable
     */
    public Sliced(final int index, final int limit,
        final Iterable<T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Sliced<>(
                    iterable.iterator(),
                    index,
                    limit
                )
            )
        );
    }
}
