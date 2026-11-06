/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.math.BigDecimal;
import java.util.Set;
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
 * <p>{@code NaN} and infinite values are summed like ordinary
 * {@code double} values: {@code NaN} propagates, and an infinity
 * absorbs any finite number.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 1.0.0
 */
public final class SumOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -5920535784882655219L;

    /**
     * Textual forms of values that {@link BigDecimal} cannot represent.
     * They are summed with ordinary IEEE-754 double arithmetic instead.
     */
    private static final Set<String> SPECIAL = Set.of(
        "NaN", "Infinity", "-Infinity"
    );

    /**
     * Ctor.
     *
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     *
     * @param src The iterable
     */
    public SumOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Folded<Number, Number>(
                    BigDecimal.ZERO,
                    (sum, value) -> {
                        final String left = sum.toString();
                        final String right = value.toString();
                        final Number total;
                        if (SumOf.SPECIAL.contains(left)
                            || SumOf.SPECIAL.contains(right)) {
                            total = sum.doubleValue() + value.doubleValue();
                        } else {
                            total = new BigDecimal(left).add(
                                new BigDecimal(right)
                            );
                        }
                        return total;
                    },
                    src
                )
            )
        );
    }
}
