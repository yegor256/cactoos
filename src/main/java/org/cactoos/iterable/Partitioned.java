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
package org.cactoos.iterable;

import java.util.List;

/**
 * Iterable implementation for partitioning functionality.
 *
 * @param <T> Partitions value type
 * @since 0.29
 */
public final class Partitioned<T> extends IterableEnvelope<List<T>> {

    /**
     * Ctor.
     * @param size The partitions size.
     * @param items The source items.
     */
    @SafeVarargs
    public Partitioned(final int size, final T...items) {
        this(size, new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param size The partitions size.
     * @param iterable The source {@link Iterable}.
     */
    public Partitioned(final int size, final Iterable<T> iterable) {
        super(() -> () -> new org.cactoos.iterator.Partitioned<>(
            size, iterable.iterator()
        ));
    }

}
