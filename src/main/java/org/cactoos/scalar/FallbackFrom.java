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
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Fallback from exception.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @since 0.31
 */
public final class FallbackFrom<T> implements Func<Throwable, T> {

    /**
     * The list of exceptions supported by this instance.
     */
    private final Iterable<Class<? extends Throwable>> exceptions;

    /**
     * Function that converts exceptions to the required type.
     */
    private final Func<Throwable, T> func;

    /**
     * Ctor.
     * @param exp Supported exception type
     * @param func Function that converts the given exception into required one
     */
    @SuppressWarnings("unchecked")
    public FallbackFrom(final Class<? extends Throwable> exp,
        final Func<Throwable, T> func) {
        this(new IterableOf<>(exp), func);
    }

    /**
     * Ctor.
     * @param exps Supported exceptions types
     * @param func Function that converts the given exception into required one
     */
    public FallbackFrom(
        final Iterable<Class<? extends Throwable>> exps,
        final Func<Throwable, T> func) {
        this.exceptions = exps;
        this.func = func;
    }

    @Override
    public T apply(final Throwable exp) throws Exception {
        return this.func.apply(exp);
    }

    /**
     * Calculate level of support of the given exception type.
     * @param target Exception type
     * @return Level of support: greater or equals to 0 if the target
     *  is supported and {@link Integer#MIN_VALUE} otherwise
     * @see InheritanceLevel
     */
    public Integer support(final Class<? extends Throwable> target) {
        return new MinOf(
            new Mapped<>(
                supported -> new InheritanceLevel(target, supported).value(),
                this.exceptions
            )
        ).intValue();
    }
}
