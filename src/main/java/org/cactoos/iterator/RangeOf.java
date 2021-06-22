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
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Iterator implementation to model range functionality.
 * @param <T> Range value type
 * @since 0.50
 */
public final class
    RangeOf<T extends Comparable<T>> implements Iterator<T> {

    /**
     * Incrementor.
     */
    private final UncheckedFunc<? super T, ? extends T> inc;

    /**
     * Current value.
     */
    private final AtomicReference<T> value;

    /**
     * Maximum value.
     */
    private final T max;

    /**
     * Ctor.
     * @param min Start of the range.
     * @param max End of the range.
     * @param incrementor The {@link Func} to process for the next value.
     */
    public RangeOf(final T min, final T max,
        final Func<? super T, ? extends T> incrementor) {
        this.inc = new UncheckedFunc<>(incrementor);
        this.value = new AtomicReference<T>(min);
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        return this.value.get().compareTo(this.max) < 1;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        final T result = this.value.get();
        this.value.set(this.inc.apply(result));
        return result;
    }
}
