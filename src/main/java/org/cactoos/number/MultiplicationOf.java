/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Constant;
import org.cactoos.scalar.Reduced;

/**
 * Multiplication result of numbers.
 *
 * <p>Here is how you can use it to multiply numbers:</p>
 *
 * <pre>
 * int multiplication = new MultiplicationOf(1, 2, 3, 4).intValue();
 * long multiplication = new MultiplicationOf(1L, 2L, 3L).longValue();
 * double multiplication = new MultiplicationOf(2.3, 3.4, 4.0).doubleValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class MultiplicationOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -2896694413969189732L;

    /**
     * Ctor.
     * @param arg Required argument
     * @param src The numbers
     */
    public MultiplicationOf(final Number arg, final Number... src) {
        this(new Joined<>(arg, new IterableOf<>(src)));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public MultiplicationOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Reduced<BigDecimal>(
                    BigDecimal::multiply,
                    new Mapped<>(
                        n -> new Constant<>(new BigDecimal(n.toString())),
                        src
                    )
                )
            )
        );
    }
}
