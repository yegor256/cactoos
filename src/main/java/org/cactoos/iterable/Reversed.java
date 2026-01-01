/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Reverse iterable.
 *
 * <p>This loads the whole wrapped {@link Iterable} in memory
 * each time {@link #iterator()} is called in order to be able to reverse it.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @see Filtered
 * @since 0.9
 */
public final class Reversed<X> extends IterableEnvelope<X> {
    /**
     * Ctor.
     * @param src Source iterable
     * @since 0.23
     */
    @SafeVarargs
    public Reversed(final X... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source iterable
     * @since 0.23
     */
    public Reversed(final Iterable<? extends X> src) {
        super(new IterableOf<>(() -> new org.cactoos.iterator.Reversed<>(src.iterator())));
    }
}
