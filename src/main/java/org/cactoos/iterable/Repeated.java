/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Scalar;

/**
 * Repeat an element.
 *
 * <p>If you need to repeat endlessly, use {@link Endless}.</p>
 *
 * @param <T> Element type
 * @since 0.1
 */
public final class Repeated<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param total The total number of repetitions
     * @param elm The element to repeat
     */
    public Repeated(final int total, final T elm) {
        this(total, () -> elm);
    }

    /**
     * Ctor.
     * @param total The total number of repetitions
     * @param elm The element to repeat
     */
    public Repeated(final int total, final Scalar<? extends T> elm) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Repeated<>(total, elm)
            )
        );
    }

}
