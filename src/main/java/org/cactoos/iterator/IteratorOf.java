/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link Iterator} that returns the elements.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of item
 * @since 0.30
 */
public final class IteratorOf<X> implements Iterator<X> {

    /**
     * The list of items to iterate.
     */
    private final X[] list;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SafeVarargs
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOf(final X... items) {
        this.list = items;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.list.length;
    }

    @Override
    public X next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.list[this.position.getAndIncrement()];
    }
}
