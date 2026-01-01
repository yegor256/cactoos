/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;

/**
 * Find the greater among items.
 *
 * <p>
 * Here is how you can use it to find the max of a set of numbers:
 * </p>
 *
 * <pre>
 * int max = new MaxOf(1, 2, 3, 4).intValue();
 * long max = new MaxOf(1L, 2L, 3L).longValue();
 * int max = new MaxOf(numbers).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class MaxOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 8337955195592696602L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public MaxOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public MaxOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::longValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::intValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::floatValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::doubleValue, src)
                )
            )
        );
    }
}
