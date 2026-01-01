/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;

/**
 * Decorator that doesn't allow any mutation of the wrapped {@link Collection}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of source item
 * @since 1.16
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public final class Immutable<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<? extends X> col;

    /**
     * Ctor.
     * @param src Source collection
     */
    public Immutable(final Collection<? extends X> src) {
        this.col = src;
    }

    @Override
    public int size() {
        return this.col.size();
    }

    @Override
    public boolean isEmpty() {
        return this.col.isEmpty();
    }

    @Override
    public Iterator<X> iterator() {
        return new org.cactoos.iterator.Immutable<>(this.col.iterator());
    }

    @Override
    public boolean contains(final Object object) {
        return this.col.contains(object);
    }

    @Override
    public Object[] toArray() {
        return this.col.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        return this.col.toArray(array);
    }

    @Override
    public boolean add(final X item) {
        throw new UnsupportedOperationException(
            "#add(): the collection is read-only"
        );
    }

    @Override
    public boolean remove(final Object object) {
        throw new UnsupportedOperationException(
            "#remove(): the collection is read-only"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> list) {
        return this.col.containsAll(list);
    }

    @Override
    public boolean addAll(final Collection<? extends X> list) {
        throw new UnsupportedOperationException(
            "#addAll(): the collection is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#removeAll(): the collection is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#retainAll(): the collection is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the collection is read-only"
        );
    }

    @Override
    public String toString() {
        return this.col.toString();
    }

    @Override
    public int hashCode() {
        return this.col.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.col.equals(obj);
    }
}
