/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Func;

/**
 * Paged iterable.
 * Elements will continue to be provided so long as {@code next} produces
 * non-empty iterators.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.47
 */
public final class Paged<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * <p>
     * @param first First bag of elements
     * @param next Subsequent bags of elements
     */
    public Paged(
        final Iterable<? extends X> first,
        final Func<? super Iterable<? extends X>, ? extends Iterable<? extends X>> next
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Paged<X>(
                    first.iterator(),
                    page -> next.apply(new IterableOf<>(page)).iterator()
                )
            )
        );
    }
}
