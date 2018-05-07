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

import org.cactoos.BiFunc;

/**
 * BiFunc that is thread-safe and sticky.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @param <X> Type of first input
 * @param <Y> Type of second input
 * @param <Z> Type of output
 * @since 0.24
 */
public final class SolidBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public SolidBiFunc(final BiFunc<X, Y, Z> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param max Max buffer length
     * @since 0.26
     */
    public SolidBiFunc(final BiFunc<X, Y, Z> fnc, final int max) {
        this.func = new SyncBiFunc<>(new StickyBiFunc<>(fnc, max));
    }

    @Override
    public Z apply(final X first, final Y second) throws Exception {
        return this.func.apply(first, second);
    }
}
