/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Head portion of the iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.8
 */
public final class HeadOf<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param num Number of head elements
     * @param src The underlying iterable
     */
    @SafeVarargs
    public HeadOf(final int num, final T... src) {
        this(num, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param num Number of head elements
     * @param iterable Decorated iterable
     */
    public HeadOf(final int num, final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.HeadOf<>(
                    num,
                    iterable.iterator()
                )
            )
        );
    }
}
