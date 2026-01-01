/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfFloats;

/**
 * Iterable of float values.
 *
 * @since 1.0
 */
public final class IterableOfFloats extends IterableEnvelope<Float> {

    /**
     * Ctor.
     * @param values Float values
     */
    public IterableOfFloats(final float... values) {
        super(new IterableOf<>(() -> new IteratorOfFloats(values)));
    }
}
