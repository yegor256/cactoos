/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.number;

import java.math.BigDecimal;
import org.cactoos.Scalar;

/**
 * Division result of two numbers.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class DivisionOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -7835189568890281261L;

    /**
     * Ctor.
     * @param dividend The dividend
     * @param divisor The divisor
     */
    public DivisionOf(final BigDecimal dividend, final BigDecimal divisor) {
        this(() -> dividend.divide(divisor));
    }

    /**
     * Ctor.
     * @param dividend The dividend
     * @param divisor The divisor
     */
    public DivisionOf(final Number dividend, final Number divisor) {
        this(
            () -> new BigDecimal(dividend.toString()).divide(
                new BigDecimal(divisor.toString())
            )
        );
    }

    /**
     * Ctor.
     * @param result The result
     */
    private DivisionOf(final Scalar<BigDecimal> result) {
        super(new NumberOfScalars(result));
    }
}
