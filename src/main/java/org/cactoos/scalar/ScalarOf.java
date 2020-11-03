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
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;

/**
 * ScalarOf.
 *
 * @param <X> Element type
 * @param <T> Element type
 * @since 0.4
 */
public final class ScalarOf<X, T> extends ScalarEnvelope<T> {
    /**
     * Ctor.
     * @param runnable Encapsulated proc
     * @param result Result to return
     * @since 0.32
     */
    public ScalarOf(final Runnable runnable, final T result) {
        this(
            () -> {
                runnable.run();
                return result;
            }
        );
    }

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param result Result to return
     * @since 0.41
     */
    public ScalarOf(final Proc<? super X> proc, final X ipt, final T result) {
        this(new FuncOf<>(proc, result), ipt);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     * @param ipt Input
     * @since 0.41
     */
    public ScalarOf(final Func<? super X, T> fnc, final X ipt) {
        this(() -> fnc.apply(ipt));
    }

    /**
     * Ctor.
     *
     * @param scalar The scalar
     */
    public ScalarOf(final Scalar<T> scalar) {
        super(scalar);
    }
}
