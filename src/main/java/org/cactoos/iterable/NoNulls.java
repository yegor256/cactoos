/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;

/**
 * A decorator for {@link Iterable} that doesn't allow any NULL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.27
 */
public final class NoNulls<X> implements Iterable<X> {

    /**
     * Original iterable.
     */
    private final Iterable<? extends X> origin;

    /**
     * Ctor.
     * @param items The items
     */
    public NoNulls(final Iterable<? extends X> items) {
        this.origin = items;
    }

    @Override
    public Iterator<X> iterator() {
        return new org.cactoos.iterator.NoNulls<>(this.origin.iterator());
    }

}
