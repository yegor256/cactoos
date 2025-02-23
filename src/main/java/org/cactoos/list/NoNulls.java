/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * A decorator of {@link List} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class NoNulls<T> implements List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param src Source
     */
    NoNulls(final List<T> src) {
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
        return new org.cactoos.collection.NoNulls<>(this.list).contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new org.cactoos.collection.NoNulls<>(this.list).iterator();
    }

    @Override
    public Object[] toArray() {
        return new org.cactoos.collection.NoNulls<>(this.list).toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return new org.cactoos.collection.NoNulls<>(this.list).toArray(array);
    }

    @Override
    public boolean add(final T item) {
        return new org.cactoos.collection.NoNulls<>(this.list).add(item);
    }

    @Override
    public boolean remove(final Object item) {
        return new org.cactoos.collection.NoNulls<>(this.list).remove(item);
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return new org.cactoos.collection.NoNulls<>(
            this.list
        ).containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        return new org.cactoos.collection.NoNulls<>(
            this.list
        ).addAll(items);
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends T> items) {
        return this.list.addAll(
            index,
            new org.cactoos.collection.NoNulls<>(items)
        );
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        return new org.cactoos.collection.NoNulls<>(this.list).removeAll(items);
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        return new org.cactoos.collection.NoNulls<>(this.list).retainAll(items);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(final int index) {
        final T item = this.list.get(index);
        if (item == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Item #%d of %s is NULL",
                        index,
                        this.list
                    )
                ).asString()
            );
        }
        return item;
    }

    @Override
    public T set(final int index, final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                new UncheckedText(
                    new FormattedText(
                        "Item can't be NULL in #set(%d,T)",
                        index
                    )
                ).asString()
            );
        }
        final T result = this.list.set(index, item);
        if (result == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Result of #set(%d,T) is NULL",
                        index
                    )
                ).asString()
            );
        }
        return result;
    }

    @Override
    public void add(final int index, final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                new UncheckedText(
                    new FormattedText(
                        "Item can't be NULL in #add(%d,T)",
                        index
                    )
                ).asString()
            );
        }
        this.list.add(index, item);
    }

    @Override
    public T remove(final int index) {
        final T result = this.list.remove(index);
        if (result == null) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Result of #remove(%d) is NULL",
                        index
                    )
                ).asString()
            );
        }
        return result;
    }

    @Override
    public int indexOf(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #indexOf(T)"
            );
        }
        return this.list.indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #lastIndexOf(T)"
            );
        }
        return this.list.lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIteratorNoNulls<>(this.list.listIterator());
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ListIteratorNoNulls<>(this.list.listIterator(index));
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return new NoNulls<>(this.list.subList(start, end));
    }
}
