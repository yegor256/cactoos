/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Reverse iterator.
 *
 * <p>This loads the whole wrapped Iterator in memory in order
 * to be able to reverse it.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 1.0
 */
public final class Reversed<X> implements Iterator<X> {

    /**
     * Origin iterator to be reversed, deferred.
     */
    private final Unchecked<ListIterator<? extends X>> origin;

    /**
     * Ctor.
     * @param src Source iterator
     * @since 1.0
     */
    @SafeVarargs
    public Reversed(final X... src) {
        this(new IteratorOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source iterator
     * @since 1.0
     */
    public Reversed(final Iterator<? extends X> src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source list
     */
    private Reversed(final List<? extends X> src) {
        this((Scalar<ListIterator<? extends X>>) () -> src.listIterator(src.size()));
    }

    /**
     * Ctor.
     * @param src Source list iterator, deferred
     */
    private Reversed(final Scalar<ListIterator<? extends X>> src) {
        this.origin = new Unchecked<>(new Sticky<>(src));
    }

    @Override
    public boolean hasNext() {
        return this.origin.value().hasPrevious();
    }

    @Override
    public X next() {
        return this.origin.value().previous();
    }
}
