/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.cactoos.scalar.Mapped;

/**
 * Iterable that returns the same set of elements, always.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.1
 */
public final class Sticky<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Sticky(final X... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param iterable The iterable
     */
    public Sticky(final Iterable<? extends X> iterable) {
        super(
            new IterableOf<>(
                new Mapped<>(
                    Iterable::iterator,
                    new org.cactoos.scalar.Sticky<>(
                        () -> new ListOf<>(iterable)
                    )
                )
            )
        );
    }

}
