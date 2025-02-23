/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.list.ListOf;

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
     * Origin iterator to be reversed.
     */
    private final ListIterator<? extends X> origin;

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
        this(src.listIterator(src.size()));
    }

    /**
     * Ctor.
     * @param src Source list iterator
     */
    private Reversed(final ListIterator<? extends X> src) {
        this.origin = src;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasPrevious();
    }

    @Override
    public X next() {
        return this.origin.previous();
    }
}
