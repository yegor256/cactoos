/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func as {@link Callable}.
 *
 * <p>You may want to use this decorator where
 * {@link Callable} is required, but you just have a function:</p>
 *
 * <pre> Callable&lt;String&gt; callable = new CallableOf&lt;&gt;(
 *   i -&gt; "Hello, world!"
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <T> Type of output
 * @since 0.12
 */
public final class CallableOf<X, T> implements Callable<T> {

    /**
     * Original callable.
     */
    private final Callable<T> callable;

    /**
     * Ctor.
     * @param runnable Encapsulated proc
     * @param result Result to return
     * @since 0.32
     */
    public CallableOf(final Runnable runnable, final T result) {
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
     * @since 0.41
     */
    public CallableOf(final Proc<X> proc, final X ipt, final T result) {
        this(new FuncOf<>(proc, result), ipt);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     * @param ipt Input
     * @since 0.41
     */
    public CallableOf(final Func<X, T> fnc, final X ipt) {
        this(() -> fnc.apply(ipt));
    }

    /**
     * Ctor.
     * @param cal Encapsulated callable
     * @since 0.41
     */
    public CallableOf(final Callable<T> cal) {
        this.callable = cal;
    }

    @Override
    public T call() throws Exception {
        return this.callable.call();
    }
}
