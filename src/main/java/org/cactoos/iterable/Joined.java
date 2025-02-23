/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;

/**
 * A few Iterables joined together.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.1
 */
public final class Joined<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    public Joined(final Iterable<? extends T>... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items Iterable
     * @since 0.32
     */
    @SuppressWarnings("unchecked")
    public Joined(final T item, final Iterable<? extends T> items) {
        this(new IterableOf<>(new IterableOf<>(item), items));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public Joined(final Iterable<? extends Iterable<? extends T>> items) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Joined<>(
                    new Mapped<Iterator<? extends T>>(Iterable::iterator, items)
                )
            )
        );
    }
}
