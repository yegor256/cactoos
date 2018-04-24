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
package org.cactoos.func;

import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.scalar.CheckedScalar;

/**
 * Procedure that accepts two arguments and throws exception of specified type.
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @param <X> Type of the first input.
 * @param <Y> Type of the second input.
 * @param <E> Exception's type.
 * @since 1.0
 */
public final class CheckedBiProc<X, Y, E extends Exception> implements
    BiProc<X, Y> {

    /**
     * Original procedure.
     */
    private final BiProc<X, Y> origin;
    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param orig Origin procedure.
     * @param fnc Function that wraps exceptions.
     */
    public CheckedBiProc(final BiProc<X, Y> orig,
        final Func<Exception, E> fnc) {
        this.origin = orig;
        this.func = fnc;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void exec(final X first, final Y second) throws E {
        new CheckedScalar<>(
            () -> {
                this.origin.exec(first, second);
                return (Void) null;
            },
            this.func
        ).value();
    }
}
