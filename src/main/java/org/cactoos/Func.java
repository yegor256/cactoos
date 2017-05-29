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

import java.util.concurrent.Callable;

/**
 * Function.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
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
     * Quiet func that returns nothing, but is still a function.
     * @param <X> Input type
     */
    interface Quiet<X> extends Func<X, Boolean> {
        @Override
        default Boolean apply(X input) throws Exception {
            this.exec(input);
            return true;
        }
        /**
         * Execute it.
         * @param input The argument
         * @throws Exception If fails
         */
        void exec(X input) throws Exception;
    }

    /**
     * Func that returns something but doesn't expect any input.
     * @param <Y> Output type
     */
    interface Value<Y> extends Func<Void, Y>, Scalar<Y>, Callable<Y> {
        @Override
        default Y apply(Void input) throws Exception {
            return this.asValue();
        }
        @Override
        default Y call() throws Exception {
            return this.asValue();
        }
    }

    /**
     * Func that really is a procedure that does something and returns nothing.
     */
    interface Proc extends Func<Boolean, Boolean>, Runnable, Callable<Boolean> {
        @Override
        default Boolean apply(Boolean input) throws Exception {
            return this.call();
        }
        @Override
        default Boolean call() throws Exception {
            this.run();
            return true;
        }
        @Override
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        default void run() {
            try {
                this.exec();
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(ex);
                // @checkstyle IllegalCatchCheck (1 line)
            } catch (final Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        /**
         * Execute it.
         * @throws Exception If fails
         */
        void exec() throws Exception;
    }

    /**
     * Func that represents a predicate of one argument.
     * @param <X> Input type
     */
    interface Pred<X> extends Func<X, Boolean> {
    }

}
