/**
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

/**
 * Procedure.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Proc}, you can use
 * {@link org.cactoos.func.UncheckedProc} decorator. Also
 * you may try {@link org.cactoos.func.IoCheckedProc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @see org.cactoos.func.FuncOf
 * @since 0.1
 */
public interface Proc<X> {

    /**
     * Execute it.
     * @param input The argument
     * @throws Exception If fails
     */
    void exec(X input) throws Exception;

    /**
     * Proc check for no nulls.
     *
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @param <X> Type of input
     * @since 0.11
     */
    final class NoNulls<X> implements Proc<X> {
        /**
         * The procedure.
         */
        private final Proc<X> origin;
        /**
         * Ctor.
         * @param proc The procedure
         */
        public NoNulls(final Proc<X> proc) {
            this.origin = proc;
        }
        @Override
        public void exec(final X input) throws Exception {
            if (this.origin == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid procedure"
                );
            }
            if (input == null) {
                throw new IllegalArgumentException(
                    "NULL instead of a valid input"
                );
            }
            this.origin.exec(input);
        }
    }
}
