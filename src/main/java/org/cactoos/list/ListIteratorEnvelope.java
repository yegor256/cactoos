/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ListIterator;

/**
 * {@link ListIterator} envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 0.47
 */
public abstract class ListIteratorEnvelope<T> implements ListIterator<T> {

    /**
     * Original list iterator.
     */
    private final ListIterator<T> origin;

    /**
     * Ctor.
     * @param iter Original list iterator.
     */
    public ListIteratorEnvelope(final ListIterator<T> iter) {
        this.origin = iter;
    }

    @Override
    public final boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public final T next() {
        return this.origin.next();
    }

    @Override
    public final boolean hasPrevious() {
        return this.origin.hasPrevious();
    }

    @Override
    public final T previous() {
        return this.origin.previous();
    }

    @Override
    public final int nextIndex() {
        return this.origin.nextIndex();
    }

    @Override
    public final int previousIndex() {
        return this.origin.previousIndex();
    }

    @Override
    public final void remove() {
        this.origin.remove();
    }

    @Override
    public final void set(final T item) {
        this.origin.set(item);
    }

    @Override
    public final void add(final T item) {
        this.origin.add(item);
    }
}
