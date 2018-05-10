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
package org.cactoos;

import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Scalar.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Scalar}, you can use
 * {@link UncheckedScalar} decorator. Also
 * you may try {@link IoCheckedScalar}.</p>
 *
 * <p>If you want to cache the result of the {@link Scalar} and
 * make sure it doesn't calculate anything twice, you can use
 * {@link StickyScalar} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see StickyScalar
 * @see UncheckedScalar
 * @see IoCheckedScalar
 * @since 0.1
 */
public interface Scalar<T> {

    /**
     * Convert it to the value.
     * @return The value
     * @throws Exception If fails
     */
    T value() throws Exception;

    /**
     * Scalar check for no nulls.
     *
     * @param <T> Type of result
     * @since 0.11
     */
    final class NoNulls<T> implements Scalar<T> {
        /**
         * The scalar.
         */
        private final Scalar<T> origin;
        /**
         * Ctor.
         * @param sclr The scalar
         */
        public NoNulls(final Scalar<T> sclr) {
            this.origin = sclr;
        }
        @Override
        public T value() throws Exception {
            if (this.origin == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid scalar"
                );
            }
            final T value = this.origin.value();
            if (value == null) {
                throw new IllegalStateException(
                    "NULL instead of a valid value"
                );
            }
            return value;
        }
    }

}
