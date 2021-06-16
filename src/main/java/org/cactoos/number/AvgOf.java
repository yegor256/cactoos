/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.util.Iterator;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;

/**
 * Average of numbers.
 *
 * <p>Here is how you can use it to find the mathematical average of numbers:
 * </p>
 *
 * <pre>
 * int avg = new AvgOf(1, 2, 3, 4).intValue();
 * long avg = new AvgOf(1L, 2L, 3L).longValue();
 * int avg = new AvgOf(numbers).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class AvgOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -5952222476772718552L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public AvgOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Ternary<>(
                    new ScalarOf<>(src::iterator),
                    (Iterator<? extends Number> it) -> it.hasNext(),
                    it -> {
                        BigDecimal total = BigDecimal.ZERO;
                        long qty = 0;
                        for (final Number value: new IterableOf<>(it)) {
                            qty = qty + 1;
                            total = total.add(
                                new BigDecimal(value.toString())
                            );
                        }
                        return total.divide(BigDecimal.valueOf(qty));
                    },
                    it -> BigDecimal.ZERO
                )
            )
        );
    }
}
