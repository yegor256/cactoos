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
package org.cactoos;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.InheritanceLevel;
import org.cactoos.scalar.MinOf;

/**
 * Fallback from a {@link Throwable}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 1.0
 */
public interface Fallback<X> extends Func<Throwable, X> {

    /**
     * Calculate level of support of the given exception type.
     * @param exception Exception
     * @return Level of support: greater or equals to 0 if the target
     *  is supported and {@link Integer#MIN_VALUE} otherwise
     */
    int support(Throwable exception);

    /**
     * Fallback from exception.
     *
     * <p>There is no thread-safety guarantee.
     *
     * @param <T> Type of result
     * @since 1.0
     */
    final class From<T> implements Fallback<T> {

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
        public From(final Class<? extends Throwable> exp,
            final Func<Throwable, T> func) {
            this(new IterableOf<>(exp), func);
        }

        /**
         * Ctor.
         * @param exps Supported exceptions types
         * @param func Function that converts the given exception into required one
         */
        public From(
            final Iterable<Class<? extends Throwable>> exps,
            final Func<Throwable, T> func) {
            this.exceptions = exps;
            this.func = func;
        }

        @Override
        public T apply(final Throwable exp) throws Exception {
            return this.func.apply(exp);
        }

        @Override
        public int support(final Throwable exception) {
            return new MinOf(
                new Mapped<>(
                    supported -> new InheritanceLevel(exception.getClass(), supported).value(),
                    this.exceptions
                )
            ).intValue();
        }
    }
}
