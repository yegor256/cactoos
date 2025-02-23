/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link Iterator} that returns the {@code boolean}s as {@link Boolean}s.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.32
 */
public final class IteratorOfBooleans implements Iterator<Boolean> {
    /**
     * The list of items to iterate.
     */
    private final boolean[] list;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOfBooleans(final boolean... items) {
        this.list = items;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.list.length;
    }

    @Override
    public Boolean next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.list[this.position.getAndIncrement()];
    }
}
