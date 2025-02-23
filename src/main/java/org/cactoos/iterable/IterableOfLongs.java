/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfLongs;

/**
 * Iterable of long values.
 *
 * @since 1.0
 */
public final class IterableOfLongs extends IterableEnvelope<Long> {

    /**
     * Ctor.
     * @param values Long values
     */
    public IterableOfLongs(final long... values) {
        super(new IterableOf<>(() -> new IteratorOfLongs(values)));
    }
}
