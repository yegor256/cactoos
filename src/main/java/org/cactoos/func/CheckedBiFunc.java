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

import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.cactoos.scalar.CheckedScalar;

/**
 * Function that accepts two arguments and throws exception of specified type.
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @param <X> Function's first input.
 * @param <Y> Function's second input.
 * @param <Z> Function's result.
 * @param <E> Exception's type.
 * @since 1.0
 */
public final class CheckedBiFunc<X, Y, Z, E extends Exception> implements
    BiFunc<X, Y, Z> {

    /**
     * Original function.
     */
    private final BiFunc<X, Y, Z> origin;
    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param orig Origin function.
     * @param fnc Function that wraps exceptions.
     */
    public CheckedBiFunc(final BiFunc<X, Y, Z> orig,
        final Func<Exception, E> fnc) {
        this.origin = orig;
        this.func = fnc;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Z apply(final X first, final Y second) throws E {
        return new CheckedScalar<>(
            () -> this.origin.apply(first, second),
            this.func
        ).value();
    }
}
