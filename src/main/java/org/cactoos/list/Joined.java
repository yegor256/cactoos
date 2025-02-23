/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.iterable.IterableOf;

/**
 * Joined list.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of source item
 * @since 0.20
 * @todo #1254:30min Make {@link Joined} implements directly {@link List}
 *  and delegate each operation to {@link JoinedListIterator} as if lists
 *  joined were one list.
 */
public final class Joined<X> extends ListEnvelope<X> {

    /**
     * Ctor.
     * @param src Source lists
     */
    @SafeVarargs
    public Joined(final List<? extends X>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items List
     * @since 0.32
     */
    @SuppressWarnings("unchecked")
    public Joined(final X item, final List<? extends X> items) {
        this(new ListOf<>(item), items);
    }

    /**
     * Ctor.
     * @param src Source lists
     */
    public Joined(final Iterable<? extends List<? extends X>> src) {
        super(
            new ListOf<>(src).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList())
        );
    }

}
