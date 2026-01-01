/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
                        long qty = 0L;
                        for (final Number value: new IterableOf<>(it)) {
                            qty += 1L;
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
