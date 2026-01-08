/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Decorator that doesn't allow mutations of the wrapped {@link Set}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.58.0
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Immutable<T> implements Set<T> {
    /**
     * Encapsulated set.
     */
    private final Set<? extends T> set;

    /**
     * Ctor.
     * @param src Source set
     */
    public Immutable(final Set<? extends T> src) {
        this.set = src;
    }

    @Override
    public int size() {
        return this.set.size();
    }

    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    @Override
    public boolean contains(final Object item) {
        return this.set.contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new org.cactoos.iterator.Immutable<>(this.set.iterator());
    }

    @Override
    public Object[] toArray() {
        return this.set.toArray();
    }

    @Override
    public <X> X[] toArray(final X[] array) {
        return this.set.toArray(array);
    }

    @Override
    public boolean add(final T item) {
        throw new UnsupportedOperationException(
            "#add(T): the set is read-only"
        );
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException(
            "#remove(Object): the set is read-only"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.set.containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(Collection): the set is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#retainAll(Collection): the set is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#removeAll(Collection): the set is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the set is read-only"
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this.set.equals(other);
    }

    @Override
    public int hashCode() {
        return this.set.hashCode();
    }

    @Override
    public String toString() {
        return this.set.toString();
    }
}
