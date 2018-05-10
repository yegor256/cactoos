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
package org.cactoos.func;

import java.util.Collections;
import org.cactoos.Func;

/**
 * Composed function.
 *
 * @param <X> Type of input.
 * @param <Y> Intermediate type.
 * @param <Z> Type of output.
 * @since 0.7
 */
public final class ChainedFunc<X, Y, Z> implements Func<X, Z> {

    /**
     * Before function.
     */
    private final Func<X, Y> before;

    /**
     * Functions.
     */
    private final Iterable<Func<Y, Y>> funcs;

    /**
     * After function.
     */
    private final Func<Y, Z> after;

    /**
     * Ctor.
     * @param bfr Before function
     * @param list Functions
     * @param atr After function
     */
    public ChainedFunc(final Func<X, Y> bfr, final Iterable<Func<Y, Y>> list,
        final Func<Y, Z> atr) {
        this.before = bfr;
        this.funcs = list;
        this.after = atr;
    }

    /**
     * Ctor.
     * @param bfr Before function
     * @param atr After function
     */
    public ChainedFunc(final Func<X, Y> bfr, final Func<Y, Z> atr) {
        this(bfr, Collections.emptyList(), atr);
    }

    @Override
    public Z apply(final X input) throws Exception {
        Y temp = this.before.apply(input);
        for (final Func<Y, Y> func : this.funcs) {
            temp = func.apply(temp);
        }
        return this.after.apply(temp);
    }
}
