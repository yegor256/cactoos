/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Tail portion of the iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> extends IterableEnvelope<T>  {

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param src The underlying iterable
     */
    @SafeVarargs
    public TailOf(final int num, final T... src) {
        this(num, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param iterable Decorated iterable
     */
    public TailOf(final int num, final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.TailOf<>(
                    num, iterable.iterator()
                )
            )
        );
    }
}
