/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
