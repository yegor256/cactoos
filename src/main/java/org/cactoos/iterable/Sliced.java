/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Sliced portion of the iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 1.0.0
 */
public final class Sliced<T> extends IterableEnvelope<T>  {

    /**
     * Ctor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param items Varargs items
     */
    @SafeVarargs
    public Sliced(final int start, final int count, final T... items) {
        this(start, count, new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param iterable Decorated iterable
     */
    public Sliced(final int start, final int count,
        final Iterable<? extends T> iterable) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Sliced<>(
                    start,
                    count,
                    iterable.iterator()
                )
            )
        );
    }
}
