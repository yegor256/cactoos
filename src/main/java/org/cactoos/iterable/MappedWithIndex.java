/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.BiFunc;

/**
 * Mapped with index iterable.
 *
 * <p>
 * There is no thread-safety guarantee.
 * @param <Y> Type of target item
 * @since 1.0.0
 */
public final class MappedWithIndex<Y> extends IterableEnvelope<Y> {
    /**
     * Ctor.
     * @param fnc Func
     * @param src Source iterable
     * @param <X> Type of source item
     */
    @SafeVarargs
    public <X> MappedWithIndex(
        final BiFunc<? super X, Integer, ? extends Y> fnc,
        final X... src
    ) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Func
     * @param src Source iterable
     * @param <X> Type of source item
     */
    public <X> MappedWithIndex(
        final BiFunc<? super X, Integer, ? extends Y> fnc,
        final Iterable<? extends X> src
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.MappedWithIndex<>(fnc, src.iterator())
            )
        );
    }
}
