/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfInts;

/**
 * Iterable of integer values.
 *
 * @since 1.0
 */
public final class IterableOfInts extends IterableEnvelope<Integer> {

    /**
     * Ctor.
     * @param values Integer values
     */
    public IterableOfInts(final int... values) {
        super(new IterableOf<>(() -> new IteratorOfInts(values)));
    }
}
