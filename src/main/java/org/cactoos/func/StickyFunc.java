/**
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
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.cactoos.scalar.StickyScalar;

/**
 * Func that caches previously calculated values and doesn't
 * recalculate again.
 *
 * <p>This {@link Func} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncFunc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @see StickyScalar
 * @since 0.1
 */
public final class StickyFunc<X, Y> implements Func<X, Y> {

    /**
     * Sticky bi-func.
     */
    private final BiFunc<X, Boolean, Y> func;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public StickyFunc(final Func<X, Y> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param max Maximum cache size
     * @since 0.26
     */
    public StickyFunc(final Func<X, Y> fnc, final int max) {
        this.func = new StickyBiFunc<>(
            (first, second) -> fnc.apply(first),
            max
        );
    }

    @Override
    public Y apply(final X input) throws Exception {
        return this.func.apply(input, true);
    }

}
