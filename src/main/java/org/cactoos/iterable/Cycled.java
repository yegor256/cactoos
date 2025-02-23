/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.iterable;

/**
 * Cycled Iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.8
 */
public final class Cycled<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param itr Iterable
     * @since 0.23
     */
    @SafeVarargs
    public Cycled(final T... itr) {
        this(new IterableOf<>(itr));
    }

    /**
     * Ctor.
     * @param itr Iterable
     */
    public Cycled(final Iterable<? extends T> itr) {
        super(new IterableOf<>(() -> new org.cactoos.iterator.Cycled<>(itr)));
    }

}
