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
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;

/**
 * Scalar that wraps an original checked exception thrown by the origin using
 * the given wrapping function.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @param <T> Type of result
 * @param <E> Type of exception
 * @since 0.30
 *
 * @todo #736:30min Create CheckedBiFunc, CheckedBiProc, CheckedFunc,
 *  CheckedProc based on this class (see CheckedBytes for example). Pay
 *  attention to a problem with testing these classes: CodeCov doesn't
 *  calculate test coverage for these classes properly dur to a problem with
 *  throwing exceptions (see https://github.com/jacoco/jacoco/issues/370). So,
 *  I would suggest to investigate into this problem at first, find solution
 *  for that and then start with implementation of the classes.
 */
public final class CheckedScalar<T, E extends Exception> implements Scalar<T> {

    /**
     * Function that wraps exception.
     */
    private final Func<Exception, E> func;

    /**
     * Original scalar.
     */
    private final Scalar<T> origin;

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     * @param fnc Func that wraps exception
     */
    public CheckedScalar(final Scalar<T> scalar,
        final Func<Exception, E> fnc) {
        this.func = fnc;
        this.origin = scalar;
    }

    @Override
    @SuppressWarnings
        (
            {
                "PMD.AvoidCatchingGenericException",
                "PMD.AvoidRethrowingException",
                "PMD.PreserveStackTrace"
            }
        )
    public T value() throws E {
        try {
            return this.origin.value();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final RuntimeException ex) {
            throw ex;
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw this.wrappedException(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw this.wrappedException(ex);
        }
    }

    /**
     * Wraps exception.
     * Skips unnecessary wrapping of exceptions of the same type.
     * Allows wrapping of exceptions of the same type if the error message
     * has been changed.
     *
     * @param exp Exception
     * @return E Wrapped exception
     */
    @SuppressWarnings("unchecked")
    private E wrappedException(final Exception exp) {
        E wrapped = new UncheckedFunc<>(this.func).apply(exp);
        final int level = new InheritanceLevel(
            exp.getClass(), wrapped.getClass()
        ).value();
        final String message = wrapped.getMessage()
            .replaceFirst(String.format("%s: ", exp.getClass().getName()), "");
        if ((level == 0 || level < Integer.MAX_VALUE)
            && message.equals(exp.getMessage())) {
            wrapped = (E) exp;
        }
        return wrapped;
    }
}
