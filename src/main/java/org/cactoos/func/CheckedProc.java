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
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.scalar.CheckedScalar;

/**
 * Proc that throws exception of specified type.
 *
 * @param <X> Type of input
 * @param <E> Exception's type
 * @since 0.32
 */
public final class CheckedProc<X, E extends Exception> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param original Original proc
     * @param fnc Function that wraps exceptions.
     */
    public CheckedProc(final Proc<X> original, final Func<Exception, E> fnc) {
        this.origin = original;
        this.func = fnc;
    }

    @Override
    public void exec(final X input) throws E {
        new CheckedScalar<>(
            () -> {
                this.origin.exec(input);
                return true;
            },
            this.func
        ).value();
    }
}
