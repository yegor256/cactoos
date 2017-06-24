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
package org.cactoos.math;

import org.cactoos.Scalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Abs of a integer number.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.9
 */
public final class IntAbs implements Scalar<Long> {

    /**
     * Scalar.
     */
    private final UncheckedScalar<Long> scalar;

    /**
     * Ctor.
     * @param number A number
     */
    public IntAbs(final Byte number) {
        this(number.longValue());
    }

    /**
     * Ctor.
     * @param number A number
     */
    public IntAbs(final Short number) {
        this(number.longValue());
    }

    /**
     * Ctor.
     * @param number A number
     */
    public IntAbs(final Integer number) {
        this(number.longValue());
    }

    /**
     * Ctor.
     * @param number A number
     */
    public IntAbs(final Long number) {
        this(() -> number);
    }

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     */
    public IntAbs(final Scalar<Long> scalar) {
        this (new UncheckedScalar<>(scalar));
    }

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     */
    public IntAbs(final UncheckedScalar<Long> scalar) {
        this.scalar = scalar;
    }

    @Override
    public Long asValue() {
        return Math.abs(this.scalar.asValue());
    }

}
