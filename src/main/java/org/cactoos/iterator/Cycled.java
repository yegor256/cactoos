/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Cycled Iterator.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.8
 */
public final class Cycled<T> implements Iterator<T> {

    /**
     * Iterable.
     */
    private final Iterable<? extends T> origin;

    /**
     * Iterator.
     */
    private final AtomicReference<Iterator<? extends T>> reference;

    /**
     * Ctor.
     * @param iterable Iterable.
     */
    public Cycled(final Iterable<? extends T> iterable) {
        this.origin = iterable;
        this.reference = new AtomicReference<>(iterable.iterator());
    }

    @Override
    public boolean hasNext() {
        if (!this.reference.get().hasNext()) {
            this.reference.compareAndSet(
                this.reference.get(),
                this.origin.iterator()
            );
        }
        return this.reference.get().hasNext();
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.reference.get().next();
    }
}
