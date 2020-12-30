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
package org.cactoos.proc;

import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Func as Runnable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
public final class RunnableOf implements Runnable {

    /**
     * Runnable.
     */
    private final Runnable runnable;

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param <X> Type of input
     * @since 0.32
     */
    public <X> RunnableOf(final Proc<? super X> proc, final X ipt) {
        this(
            () -> {
                new UncheckedProc<>(proc).exec(ipt);
            }
        );
    }

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     * @since 0.11
     */
    public RunnableOf(final Scalar<?> scalar) {
        this(
            () -> {
                new Unchecked<>(scalar).value();
            }
        );
    }

    /**
     * Ctor.
     * @param runnable Encapsulated runnable
     * @since 0.49
     */
    public RunnableOf(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
