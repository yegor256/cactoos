/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.list;

import java.util.ListIterator;

/**
 * A decorator of {@link ListIterator} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.39
 */
public final class ListIteratorNoNulls<T> implements ListIterator<T> {

    /**
     * ListIterator.
     */
    private final ListIterator<T> listiterator;

    /**
     * Ctor.
     *
     * @param src List iterator.
     */
    public ListIteratorNoNulls(final ListIterator<T> src) {
        this.listiterator = src;
    }

    @Override
    public boolean hasNext() {
        return this.listiterator.hasNext();
    }

    @Override
    public T next() {
        final T next = this.listiterator.next();
        if (next == null) {
            throw new IllegalStateException("Next item is NULL");
        }
        return next;
    }

    @Override
    public boolean hasPrevious() {
        return this.listiterator.hasPrevious();
    }

    @Override
    public T previous() {
        final T prev = this.listiterator.previous();
        if (prev == null) {
            throw new IllegalStateException("Previous item is NULL");
        }
        return prev;
    }

    @Override
    public int nextIndex() {
        return this.listiterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.listiterator.previousIndex();
    }

    @Override
    public void remove() {
        this.listiterator.remove();
    }

    @Override
    public void set(final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #set(T)"
            );
        }
        this.listiterator.set(item);
    }

    @Override
    public void add(final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #add(T)"
            );
        }
        this.listiterator.add(item);
    }

}
