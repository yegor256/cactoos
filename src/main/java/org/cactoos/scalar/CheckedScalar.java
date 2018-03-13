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
 * Scalar that throws exception specified by user.
 *
 *  <p>There is no thread-safety guarantee.
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @param <T> Type of result
 * @param <E> Type of exception
 * @since 0.30
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
     * @param func Func that wraps exception
     */
    public CheckedScalar(final Scalar<T> scalar,
        final Func<Exception, E> func) {
        this.func = func;
        this.origin = scalar;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public T value() throws E {
        try {
            return this.origin.value();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final RuntimeException ex) {
            throw this.wrappedException(ex);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw this.wrappedException(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw this.wrappedException(ex);
        }
    }

    /**
     * Wraps exception. Skips unnecessary boxing of exception.
     * @param exp Exception
     * @return E Wrapped exception
     */
    @SuppressWarnings("unchecked")
    private E wrappedException(final Exception exp) {
        E wrapped = new UncheckedFunc<>(this.func).apply(exp);
        final int level = new InheritanceLevel(
            exp.getClass(), wrapped.getClass()
        ).value();
        final int unrelated = 999;
        if (level == 0 || level < unrelated) {
            wrapped = (E) exp;
        }
        return wrapped;
    }
}
