/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfBooleans;

/**
 * Iterable of boolean values.
 *
 * @since 1.0
 */
public final class IterableOfBooleans extends IterableEnvelope<Boolean> {

    /**
     * Ctor.
     * @param values Boolean values
     */
    public IterableOfBooleans(final boolean... values) {
        super(new IterableOf<>(() -> new IteratorOfBooleans(values)));
    }
}
