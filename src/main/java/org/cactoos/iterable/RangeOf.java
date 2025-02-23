/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Func;

/**
 * Iterable implementation to model range functionality.
 * @param <T> Range value type
 * @since 1.0
 */
public final class
    RangeOf<T extends Comparable<T>> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param min Start of the range.
     * @param max End of the range.
     * @param incrementor The {@link Func} to process for the next value.
     */
    public RangeOf(final T min, final T max,
        final Func<? super T, ? extends T> incrementor) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.RangeOf<>(min, max, incrementor)
            )
        );
    }
}
