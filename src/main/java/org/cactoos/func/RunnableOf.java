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

import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func as Runnable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.12
 */
public final class RunnableOf<X> implements Runnable {

    /**
     * Original proc.
     */
    private final Proc<X> proc;

    /**
     * The input.
     */
    private final X input;

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @since 0.11
     */
    public RunnableOf(final Callable<X> proc) {
        this(new FuncOf<>(proc));
    }

    /**
     * Ctor.
     * @param proc Encapsulated proc
     */
    public RunnableOf(final Proc<X> proc) {
        this(proc, null);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public RunnableOf(final Func<X, ?> fnc) {
        this(fnc, null);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     * @param ipt Input
     */
    public RunnableOf(final Func<X, ?> fnc, final X ipt) {
        this(new ProcOf<>(fnc), ipt);
    }

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @since 0.32
     */
    public RunnableOf(final Proc<X> proc, final X ipt) {
        this.proc = proc;
        this.input = ipt;
    }

    @Override
    public void run() {
        new UncheckedProc<>(this.proc).exec(this.input);
    }
}
