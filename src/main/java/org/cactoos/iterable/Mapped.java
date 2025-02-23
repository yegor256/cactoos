/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Func;

/**
 * Mapped iterable.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <Y> Type of target item
 * @since 0.1
 */
public final class Mapped<Y> extends IterableEnvelope<Y> {

    /**
     * Ctor.
     * @param fnc Func
     * @param src Source iterable
     * @param <X> Type of source item
     */
    @SafeVarargs
    public <X> Mapped(final Func<? super X, ? extends Y> fnc, final X... src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Func
     * @param src Source iterable
     * @param <X> Type of source item
     */
    public <X> Mapped(
        final Func<? super X, ? extends Y> fnc, final Iterable<? extends X> src
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Mapped<>(fnc, src.iterator())
            )
        );
    }
}
