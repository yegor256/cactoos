/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
