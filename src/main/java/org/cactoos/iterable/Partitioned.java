/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.List;

/**
 * Iterable implementation for partitioning functionality.
 *
 * @param <T> Partitions value type
 * @since 0.29
 */
public final class Partitioned<T> extends IterableEnvelope<List<T>> {

    /**
     * Ctor.
     * @param size The partitions size.
     * @param items The source items.
     */
    @SafeVarargs
    public Partitioned(final int size, final T... items) {
        this(size, new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param size The partitions size.
     * @param iterable The source {@link Iterable}.
     */
    public Partitioned(final int size, final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Partitioned<>(
                    size,
                    iterable.iterator()
                )
            )
        );
    }

}
