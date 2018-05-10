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

import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.FallbackFrom;
import org.cactoos.scalar.ScalarWithFallback;

/**
 * Func with a fallback plan.
 *
 * <p>There is no thread-safety guarantee.
 *
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
     * The fallbacks.
     */
    private final Iterable<FallbackFrom<Y>> fallbacks;

    /**
     * The follow up.
     */
    private final Func<Y, Y> follow;

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     */
    @SuppressWarnings("unchecked")
    public FuncWithFallback(final Func<X, Y> fnc, final FallbackFrom<Y> fbk) {
        this(fnc, new IterableOf<>(fbk));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     * @param flw The follow up func
     */
    @SuppressWarnings("unchecked")
    public FuncWithFallback(final Func<X, Y> fnc, final FallbackFrom<Y> fbk,
        final Func<Y, Y> flw) {
        this(fnc, new IterableOf<>(fbk), flw);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbks The fallbacks
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Iterable<FallbackFrom<Y>> fbks) {
        this(fnc, fbks, input -> input);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbks The fallbacks
     * @param flw The follow up func
     */
    public FuncWithFallback(final Func<X, Y> fnc,
        final Iterable<FallbackFrom<Y>> fbks, final Func<Y, Y> flw) {
        this.func = fnc;
        this.fallbacks = fbks;
        this.follow = flw;
    }

    @Override
    public Y apply(final X input) throws Exception {
        return new ScalarWithFallback<>(
            () -> this.func.apply(input),
            this.fallbacks,
            this.follow
        ).value();
    }

}
