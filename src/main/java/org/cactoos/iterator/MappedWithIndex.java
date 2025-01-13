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
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.BiFunc;

/**
 * Mapped with index iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * @param <Y> Type of target item
 * @since 1.0.0
 */
public final class MappedWithIndex<Y> extends IteratorEnvelope<Y> {
    /**
     * Ctor.
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     */
    public <X> MappedWithIndex(
        final BiFunc<? super X, Integer, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        this(
            new AtomicInteger(-1),
            func,
            iterator
        );
    }

    /**
     * Privated Ctor.
     * @param indexcounter Index Counter
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     */
    private <X> MappedWithIndex(
        final AtomicInteger indexcounter,
        final BiFunc<? super X, Integer, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        super(
            new Mapped<>(
                item -> func.apply(item, indexcounter.incrementAndGet()),
                iterator
            )
        );
    }
}
