/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;

/**
 * Find the smaller among items.
 *
 * <p>
 * Here is how you can use it to find the min of a set of numbers:
 * </p>
 *
 * <pre>
 * int min = new MinOf(1, 2, 3, 4).intValue();
 * long min = new MinOf(1L, 2L, 3L).longValue();
 * int min = new MinOf(numbers).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class MinOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -7540548570249911487L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public MinOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public MinOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Reduced<>(
                    Math::min,
                    new Mapped<>((Number n) -> n::longValue, src)
                ),
                new Reduced<>(
                    Math::min,
                    new Mapped<>((Number n) -> n::intValue, src)
                ),
                new Reduced<>(
                    Math::min,
                    new Mapped<>((Number n) -> n::floatValue, src)
                ),
                new Reduced<>(
                    Math::min,
                    new Mapped<>((Number n) -> n::doubleValue, src)
                )
            )
        );
    }
}
