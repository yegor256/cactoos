/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link Iterator} that returns the {@code long}s as {@link Long}s.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.34
 */
public final class IteratorOfLongs implements Iterator<Long> {

    /**
     * The list of items to iterate.
     */
    private final long[] items;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param itms Items to iterate
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOfLongs(final long... itms) {
        this.items = itms;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.items.length;
    }

    @Override
    public Long next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.items[this.position.getAndIncrement()];
    }
}
