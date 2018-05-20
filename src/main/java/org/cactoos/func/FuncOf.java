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
import org.cactoos.Scalar;

/**
 * Represents many possible inputs as {@link Func}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.12
 */
public final class FuncOf<X, Y> implements Func<X, Y> {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param result The result
     */
    public FuncOf(final Y result) {
        this((Func<X, Y>) input -> result);
    }

    /**
     * Ctor.
     * @param callable The callable
     */
    public FuncOf(final Callable<Y> callable) {
        this((Func<X, Y>) input -> callable.call());
    }

    /**
     * Ctor.
     * @param runnable The runnable
     * @param result Result to return
     * @since 0.32
     */
    public FuncOf(final Runnable runnable, final Y result) {
        this(input -> runnable.run(), result);
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param result Result to return
     */
    public FuncOf(final Proc<X> proc, final Y result) {
        this(
            input -> {
                proc.exec(input);
                return result;
            }
        );
    }

    /**
     * Ctor.
     * @param scalar Origin scalar
     */
    public FuncOf(final Scalar<Y> scalar) {
        this(input -> {
            return scalar.value();
        });
    }

    /**
     * Ctor.
     * @param fnc Func
     */
    private FuncOf(final Func<X, Y> fnc) {
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) throws Exception {
        return this.func.apply(input);
    }
}
