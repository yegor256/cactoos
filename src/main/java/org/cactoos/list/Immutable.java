/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Decorator that doesn't allow mutations of the wrapped {@link List}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 1.16
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class Immutable<T> implements List<T> {

    /**
     * Encapsulated list.
     */
    private final List<? extends T> list;

    /**
     * Ctor.
     * @param src Source collection
     */
    public Immutable(final List<? extends T> src) {
        this.list = src;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(final Object item) {
        return this.list.contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new org.cactoos.iterator.Immutable<>(this.list.iterator());
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return this.list.toArray(array);
    }

    @Override
    public boolean add(final T item) {
        throw new UnsupportedOperationException(
            "#add(T): the list is read-only"
        );
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException(
            "#remove(Object): the list is read-only"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.list.containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(Collection): the list is read-only"
        );
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(int, Collection): the list is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#removeAll(): the list is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#retainAll(): the list is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the list is read-only"
        );
    }

    @Override
    public T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public T set(final int index, final T item) {
        throw new UnsupportedOperationException(
            "#set(): the list is read-only"
        );
    }

    @Override
    public void add(final int index, final T item) {
        throw new UnsupportedOperationException(
            "#add(int, T): the list is read-only"
        );
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException(
            "#remove(int): the list is read-only"
        );
    }

    @Override
    public int indexOf(final Object item) {
        return this.list.indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        return this.list.lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ImmutableListIterator<>(
            this.list.listIterator()
        );
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ImmutableListIterator<>(
            this.list.listIterator(index)
        );
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return new Immutable<>(
            this.list.subList(start, end)
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this.list.equals(other);
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
