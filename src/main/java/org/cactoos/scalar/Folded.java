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
package org.cactoos.scalar;

import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable, which elements are "folded" through the func.
 *
 * @param <T> Type of element
 * @param <X> Type of input and output
 * @since 0.30
 */
public final class Folded<X, T> implements Scalar<X> {

    /**
     * Original iterable.
     */
    private final Iterable<T> iterable;

    /**
     * Input.
     */
    private final X input;

    /**
     * Func.
     */
    private final BiFunc<X, T, X> func;

    /**
     * Ctor.
     * @param ipt Input
     * @param fnc Func original
     * @param list List of items
     */
    public Folded(final X ipt, final BiFunc<X, T, X> fnc,
        final Iterable<T> list) {
        this.iterable = list;
        this.input = ipt;
        this.func = fnc;
    }

    /**
     * Ctor.
     * @param ipt Input
     * @param fnc Func original
     * @param list Array of items
     */
    @SafeVarargs
    public Folded(final X ipt, final BiFunc<X, T, X> fnc,
        final T... list) {
        this(ipt, fnc, new IterableOf<>(list));
    }

    @Override
    public X value() throws Exception {
        X memo = this.input;
        for (final T item : this.iterable) {
            memo = this.func.apply(memo, item);
        }
        return memo;
    }

}
