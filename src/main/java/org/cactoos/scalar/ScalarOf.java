/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.FuncOf;

/**
 * ScalarOf.
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class ScalarOf<T> extends ScalarEnvelope<T> {
    /**
     * Ctor.
     * @param runnable Encapsulated proc
     * @param result Result to return
     * @since 0.48
     */
    public ScalarOf(final Runnable runnable, final T result) {
        this(() -> {
            runnable.run();
            return result;
        });
    }

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param result Result to return
     * @param <X> Type of input
     * @since 0.48
     */
    public <X> ScalarOf(final Proc<? super X> proc, final X ipt, final T result) {
        this(new FuncOf<>(proc, result), ipt);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     * @param ipt Input
     * @param <X> Type of input
     * @since 0.41
     */
    public <X> ScalarOf(final Func<? super X, ? extends T> fnc, final X ipt) {
        this(() -> fnc.apply(ipt));
    }

    /**
     * Ctor.
     *
     * @param origin The scalar
     */
    public ScalarOf(final Callable<? extends T> origin) {
        super(origin::call);
    }
}
