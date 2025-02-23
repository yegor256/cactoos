/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Comparator;

/**
 * Sorted iterable.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.7
 */
public final class Sorted<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Sorted(final T... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     *
     * <p>If you're using this ctor you must be sure that type {@code T}
     * implements {@link Comparable} interface. Otherwise, there will be
     * a type casting exception in runtime.</p>
     *
     * @param src The underlying iterable
     */
    @SuppressWarnings("unchecked")
    public Sorted(final Iterable<? extends T> src) {
        this((Comparator<? super T>) Comparator.naturalOrder(), src);
    }

    /**
     * Ctor.
     * @param cmp The comparator
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Sorted(final Comparator<? super T> cmp, final T... src) {
        this(cmp, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param cmp The comparator
     * @param src The underlying iterable
     */
    public Sorted(final Comparator<? super T> cmp, final Iterable<? extends T> src) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Sorted<>(cmp, src.iterator())
            )
        );
    }
}
