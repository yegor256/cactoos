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
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Folded;

/**
 * Sums of an iterable of numbers.
 *
 * <p>Here is how you can use it to summarize numbers:</p>
 *
 * <pre>
 * int sum = new SumOf(1, 2, 3, 4).intValue();
 * long sum = new SumOf(1L, 2L, 3L).longValue();
 * int sum = new SumOf(numbers).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class SumOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -5920535784882655219L;

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public SumOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Folded<>(
                    BigDecimal.ZERO,
                    (sum, value) -> sum.add(new BigDecimal(value.toString())),
                    src
                )
            )
        );
    }
}
