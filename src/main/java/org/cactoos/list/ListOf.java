/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.iterable.IterableOf;

/**
 * Implementation of {@link List}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> List type
 * @since 0.1
 */
public final class ListOf<T> extends ListEnvelope<T> {
    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    public ListOf(final T... array) {
        this(new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param src An {@link Iterator}
     * @since 0.21
     */
    public ListOf(final Iterator<? extends T> src) {
        this(new IterableOf<T>(src));
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public ListOf(final Iterable<? extends T> src) {
        super(new LinkedList<>());
        src.forEach(super::add);
    }
}
