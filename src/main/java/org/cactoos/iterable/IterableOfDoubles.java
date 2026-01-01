/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfDoubles;

/**
 * Iterable of double values.
 *
 * @since 1.0
 */
public final class IterableOfDoubles extends IterableEnvelope<Double> {

    /**
     * Ctor.
     * @param values Double values
     */
    public IterableOfDoubles(final double... values) {
        super(new IterableOf<>(() -> new IteratorOfDoubles(values)));
    }
}
