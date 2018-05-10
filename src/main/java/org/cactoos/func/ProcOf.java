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
 * Func as Proc.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.12
 */
public final class ProcOf<X> implements Proc<X> {

    /**
     * The proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     * @param runnable The runnable
     */
    public ProcOf(final Runnable runnable) {
        this((Proc<X>) input -> runnable.run());
    }

    /**
     * Ctor.
     * @param callable The callable
     */
    public ProcOf(final Callable<X> callable) {
        this((Proc<X>) input -> callable.call());
    }

    /**
     * Ctor.
     * @param fnc The proc
     */
    public ProcOf(final Func<X, ?> fnc) {
        this((Proc<X>) fnc::apply);
    }

    /**
     * Ctor.
     * @param prc The proc
     */
    private ProcOf(final Proc<X> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X input) throws Exception {
        this.proc.exec(input);
    }
}
