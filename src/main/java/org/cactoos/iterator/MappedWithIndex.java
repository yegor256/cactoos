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
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.BiFunc;
import org.cactoos.scalar.ScalarWithIndex;
import org.cactoos.scalar.Unchecked;

/**
 * Mapped with index iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * @param <Y> Type of target item
 * @since 0.50
 */
public final class MappedWithIndex<Y> extends IteratorEnvelope<Y> {
    /**
     * Ctor.
     * @param func Func
     * @param iterator Source iterator
     * @param <X> Type of item
     * @checkstyle AnonInnerLengthCheck (60 lines)
     */
    public <X> MappedWithIndex(
        final BiFunc<Integer, ? super X, ? extends Y> func,
        final Iterator<? extends X> iterator
    ) {
        super(
            new Iterator<Y>() {
                private final AtomicInteger indexcount = new AtomicInteger(-1);

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public Y next() {
                    if (this.hasNext()) {
                        final int index = this.indexcount.incrementAndGet();
                        return new Unchecked<>(
                            new org.cactoos.scalar.MappedWithIndex<>(
                                func, new ScalarWithIndex<>(index, iterator::next)
                            )
                        ).value();
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    iterator.remove();
                    this.indexcount.getAndDecrement();
                }
            }
        );
    }
}
