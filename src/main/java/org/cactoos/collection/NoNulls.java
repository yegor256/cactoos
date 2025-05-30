/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * A decorator of {@link Collection} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Element type
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class NoNulls<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<X> col;

    /**
     * Ctor without '? extends X' wildcard because of the issue:
     *  <ol>
     *      <li>The client gets Collection of X</li>
     *      <li>The client adds item of X to it</li>
     *      <li>The client queries items of original Collection of ? extends X</li>
     *      <li>Runtime exception occurs</li>
     *  </ol>
     * @param items Original one.
     */
    public NoNulls(final Collection<X> items) {
        this.col = items;
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
        return new org.cactoos.iterator.NoNulls<>(this.col.iterator());
    }

    @Override
    public boolean contains(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Argument of #contains(T) is NULL"
            );
        }
        return this.col.contains(item);
    }

    @Override
    public Object[] toArray() {
        final Object[] array = this.col.toArray();
        for (int idx = 0; idx < array.length; ++idx) {
            if (array[idx] == null) {
                throw new IllegalStateException(
                    new UncheckedText(
                        new FormattedText(
                            "Item #%d of #toArray() is NULL", idx
                        )
                    ).asString()
                );
            }
        }
        return array;
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        this.col.toArray((Object[]) array);
        for (int idx = 0; idx < array.length; ++idx) {
            if (array[idx] == null) {
                throw new IllegalStateException(
                    new UncheckedText(
                        new FormattedText(
                            "Item #%d of #toArray(array) is NULL", idx
                        )
                    ).asString()
                );
            }
        }
        return array;
    }

    @Override
    public boolean add(final X item) {
        if (item == null) {
            throw new IllegalStateException(
                "Item of #add(T) is NULL"
            );
        }
        return this.col.add(item);
    }

    @Override
    public boolean remove(final Object item) {
        if (item == null) {
            throw new IllegalStateException(
                "Item of #remove(T) is NULL"
            );
        }
        return this.col.remove(item);
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.col.containsAll(new NoNulls<>(items));
    }

    @Override
    public boolean addAll(final Collection<? extends X> items) {
        return this.col.addAll(new NoNulls<>(items));
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        return this.col.removeAll(new NoNulls<>(items));
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        return this.col.retainAll(new NoNulls<>(items));
    }

    @Override
    public void clear() {
        this.col.clear();
    }

    @Override
    public String toString() {
        return this.col.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.col.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.col.hashCode();
    }
}
