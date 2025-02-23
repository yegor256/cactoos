/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfShorts;

/**
 * Iterable of short values.
 *
 * @since 1.0
 */
public final class IterableOfShorts extends IterableEnvelope<Short> {

    /**
     * Ctor.
     * @param values Short values
     */
    @SuppressWarnings("PMD.AvoidUsingShortType")
    public IterableOfShorts(final short... values) {
        super(new IterableOf<>(() -> new IteratorOfShorts(values)));
    }
}
