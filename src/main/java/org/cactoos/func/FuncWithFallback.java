/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
 * Func with a fallback plan.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.2
 */
public final class FuncWithFallback<X, Y> implements Func<X, Y> {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * The fallback.
     */
    private final Func<Throwable, Y> fallback;

    /**
     * The follow up.
     */
    private final Func<Y, Y> follow;

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Func<Throwable, Y> fbk) {
        this(fnc, fbk, input -> input);
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param fbk The fallback
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> proc,
        final Func<Throwable, Y> fbk) {
        this(new FuncOf<>(proc), fbk);
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param fbk The fallback
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> proc,
        final Proc<Throwable> fbk) {
        this(new FuncOf<>(proc), new FuncOf<>(fbk));
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param fbk The fallback
     * @since 0.12
     */
    public FuncWithFallback(final Func<X, Y> proc,
        final Proc<Throwable> fbk) {
        this(proc, new FuncOf<>(fbk));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Proc<Throwable> fbk, final Func<Y, Y> flw) {
        this(fnc, new FuncOf<>(fbk), flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Proc<Throwable> fbk, final Proc<Y> flw) {
        this(fnc, new FuncOf<>(fbk), new FuncOf<>(flw));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Func<Throwable, Y> fbk, final Proc<Y> flw) {
        this(fnc, fbk, new FuncOf<>(flw));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> fnc,
        final Func<Throwable, Y> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> fnc,
        final Proc<Throwable> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> fnc,
        final Proc<Throwable> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Proc<X> fnc,
        final Func<Throwable, Y> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Callable<Y> fnc,
        final Func<Throwable, Y> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Callable<Y> fnc,
        final Proc<Throwable> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Callable<Y> fnc,
        final Proc<Throwable> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Callable<Y> fnc,
        final Func<Throwable, Y> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Runnable fnc,
        final Func<Throwable, Y> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Runnable fnc,
        final Func<Throwable, Y> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Runnable fnc,
        final Proc<Throwable> fbk, final Func<Y, Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     * @since 0.12
     */
    public FuncWithFallback(final Runnable fnc,
        final Proc<Throwable> fbk, final Proc<Y> flw) {
        this(new FuncOf<>(fnc), fbk, flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Func<Throwable, Y> fbk, final Func<Y, Y> flw) {
        this.func = fnc;
        this.fallback = fbk;
        this.follow = flw;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public Y apply(final X input) throws Exception {
        Y result;
        try {
            result = this.func.apply(input);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            result = this.fallback.apply(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Throwable ex) {
            result = this.fallback.apply(ex);
        }
        return this.follow.apply(result);
    }

}
