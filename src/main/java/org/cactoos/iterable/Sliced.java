/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param items Varargs items
     */
    @SafeVarargs
    public Sliced(final int start, final int count, final T... items) {
        this(start, count, new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param iterable Decorated iterable
     */
    public Sliced(final int start, final int count,
        final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Sliced<>(
                    start,
                    count,
                    iterable.iterator()
                )
            )
        );
    }
}
