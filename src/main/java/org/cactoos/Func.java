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
package org.cactoos;

import java.io.IOException;

/**
 * Function.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Func}, you can use
 * {@link org.cactoos.func.UncheckedFunc} decorator. Also
 * you may try {@link org.cactoos.func.IoCheckedFunc}.</p>
 *
 * <p>If you want to cache the result of the {@link Func} and
 * make sure it doesn't calculate anything twice, you can use
 * {@link org.cactoos.func.StickyFunc} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @see org.cactoos.func.StickyFunc
 * @see org.cactoos.func.UncheckedFunc
 * @see org.cactoos.func.IoCheckedFunc
 * @since 0.1
 */
public interface Func<X, Y> {

    /**
     * Apply it.
     * @param input The argument
     * @return The result
     * @throws Exception If fails
     */
    Y apply(X input) throws Exception;

    /**
     * Func check for no nulls.
     *
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @param <X> Type of input
     * @param <Y> Type of output
     * @since 0.10
     */
    final class NoNulls<X, Y> implements Func<X, Y> {
        /**
         * The function.
         */
        private final Func<X, Y> func;
        /**
         * Ctor.
         * @param fnc The function
         */
        public NoNulls(final Func<X, Y> fnc) {
            this.func = fnc;
        }
        @Override
        public Y apply(final X input) throws Exception {
            if (this.func == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid function"
                );
            }
            if (input == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid input"
                );
            }
            final Y result = this.func.apply(input);
            if (result == null) {
                throw new IOException("NULL instead of a valid result");
            }
            return result;
        }
    }
}
