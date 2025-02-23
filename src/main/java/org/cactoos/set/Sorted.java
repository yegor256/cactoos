/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable as Sorted {@link Set} based on {@link TreeSet}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Set type
 * @since 1.0.0
 */
public final class Sorted<T> extends SortedSetEnvelope<T> {

    /**
     * Ctor.
     * @param cmp Comparator
     * @param array An array of some elements
     */
    @SafeVarargs
    public Sorted(final Comparator<? super T> cmp, final T... array) {
        this(cmp, new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param cmp Comparator
     * @param src An {@link Iterable}
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public Sorted(
        final Comparator<? super T> cmp,
        final Iterable<? extends T> src
    ) {
        super(new TreeSet<>(cmp));
        src.forEach(super::add);
    }
}
