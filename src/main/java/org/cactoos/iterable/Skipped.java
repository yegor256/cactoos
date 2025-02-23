/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Skipped iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.34
 */
public final class Skipped<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param skip How many to skip
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Skipped(final int skip, final T... src) {
        this(skip, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param skip Count skip elements
     * @param iterable Decorated iterable
     */
    public Skipped(final int skip, final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Skipped<>(
                    skip,
                    iterable.iterator()
                )
            )
        );
    }
}
