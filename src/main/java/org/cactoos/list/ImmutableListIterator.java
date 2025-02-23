/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ListIterator;

/**
 * Immutable {@link ListIterator} that doesn't allow mutations.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 1.0
 */
public final class ImmutableListIterator<T> implements ListIterator<T> {

    /**
     * Original list iterator.
     */
    private final ListIterator<? extends T> origin;

    /**
     * Ctor.
     * @param iter Original list iterator.
     */
    public ImmutableListIterator(final ListIterator<? extends T> iter) {
        this.origin = iter;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public T next() {
        return this.origin.next();
    }

    @Override
    public boolean hasPrevious() {
        return this.origin.hasPrevious();
    }

    @Override
    public T previous() {
        return this.origin.previous();
    }

    @Override
    public int nextIndex() {
        return this.origin.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.origin.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow removing items"
        );
    }

    @Override
    public void set(final T item) {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow rewriting items"
        );
    }

    @Override
    public void add(final T item) {
        throw new UnsupportedOperationException(
            "List Iterator is read-only and doesn't allow adding items"
        );
    }
}
