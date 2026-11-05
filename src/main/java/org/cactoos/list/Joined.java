/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterable.Sliced;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Joined list.
 *
 * <p>The sources are not copied. Every operation is delegated to
 * {@link JoinedListIterator}, which traverses the source lists lazily,
 * as if they were one list.</p>
 *
 * <p>The list is read-only: all mutating operations throw
 * {@link UnsupportedOperationException}, because there is no
 * unambiguous way to modify the sources of a joined list.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of source item
 * @since 0.20
 */
public final class Joined<X> implements List<X> {

    /**
     * Source lists, materialized lazily on first use.
     */
    private final Unchecked<List<? extends List<? extends X>>> sources;

    /**
     * Ctor.
     *
     * @param src Source lists
     */
    @SafeVarargs
    public Joined(final List<? extends X>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     *
     * @param item First item
     * @param items List
     * @since 0.32
     */
    @SuppressWarnings("unchecked")
    public Joined(final X item, final List<? extends X> items) {
        this(new ListOf<>(item), items);
    }

    /**
     * Ctor.
     *
     * @param src Source lists
     */
    public Joined(final Iterable<? extends List<? extends X>> src) {
        this.sources = new Unchecked<>(new Sticky<>(() -> new ListOf<>(src)));
    }

    @Override
    public int size() {
        int size = 0;
        final ListIterator<X> iter = this.listIterator();
        while (iter.hasNext()) {
            iter.next();
            ++size;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return !this.listIterator().hasNext();
    }

    @Override
    public boolean contains(final Object item) {
        return this.indexOf(item) >= 0;
    }

    @Override
    public Iterator<X> iterator() {
        return this.listIterator();
    }

    @Override
    public Object[] toArray() {
        final List<X> items = new ArrayList<>(0);
        final Iterator<X> iter = this.iterator();
        while (iter.hasNext()) {
            items.add(iter.next());
        }
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] array) {
        final List<X> items = new ArrayList<>(0);
        final Iterator<X> iter = this.iterator();
        while (iter.hasNext()) {
            items.add(iter.next());
        }
        return items.toArray(array);
    }

    @Override
    public boolean add(final X item) {
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
        boolean contains = true;
        for (final Object item : items) {
            if (!this.contains(item)) {
                contains = false;
                break;
            }
        }
        return contains;
    }

    @Override
    public boolean addAll(final Collection<? extends X> items) {
        throw new UnsupportedOperationException(
            "#addAll(Collection): the list is read-only"
        );
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends X> items) {
        throw new UnsupportedOperationException(
            "#addAll(int, Collection): the list is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#removeAll(Collection): the list is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#retainAll(Collection): the list is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the list is read-only"
        );
    }

    @Override
    public X get(final int index) {
        final ListIterator<X> iter = this.listIterator(index);
        if (!iter.hasNext()) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "index %d is out of the list bounds (size %d)",
                    index,
                    this.size()
                )
            );
        }
        return iter.next();
    }

    @Override
    public X set(final int index, final X element) {
        throw new UnsupportedOperationException(
            "#set(int, T): the list is read-only"
        );
    }

    @Override
    public void add(final int index, final X element) {
        throw new UnsupportedOperationException(
            "#add(int, T): the list is read-only"
        );
    }

    @Override
    public X remove(final int index) {
        throw new UnsupportedOperationException(
            "#remove(int): the list is read-only"
        );
    }

    @Override
    public int indexOf(final Object item) {
        int found = -1;
        int index = 0;
        final ListIterator<X> iter = this.listIterator();
        while (found < 0 && iter.hasNext()) {
            if (Objects.equals(item, iter.next())) {
                found = index;
            }
            ++index;
        }
        return found;
    }

    @Override
    public int lastIndexOf(final Object item) {
        int found = -1;
        int index = 0;
        final ListIterator<X> iter = this.listIterator();
        while (iter.hasNext()) {
            if (Objects.equals(item, iter.next())) {
                found = index;
            }
            ++index;
        }
        return found;
    }

    @Override
    public ListIterator<X> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<X> listIterator(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(
                String.format("index %d must not be negative", index)
            );
        }
        final ListIterator<X> iter = this.joinedIterator();
        for (int seen = 0; seen < index; ++seen) {
            if (!iter.hasNext()) {
                throw new IndexOutOfBoundsException(
                    String.format(
                        "index %d is out of the list bounds (size %d)",
                        index,
                        seen
                    )
                );
            }
            iter.next();
        }
        return iter;
    }

    @Override
    public List<X> subList(final int start, final int end) {
        final int size = this.size();
        if (start < 0 || end > size) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "subList(%d, %d) is out of the list bounds (size %d)",
                    start,
                    end,
                    size
                )
            );
        }
        if (start > end) {
            throw new IllegalArgumentException(
                String.format(
                    "subList start (%d) must not be greater than end (%d)",
                    start,
                    end
                )
            );
        }
        return new ListOf<>(
            new Sliced<>(start, end - start, this)
        );
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof List)) {
            return false;
        }
        final Iterator<?> theirs = ((List<?>) other).iterator();
        final Iterator<X> ours = this.iterator();
        boolean equal = true;
        while (equal && ours.hasNext()) {
            if (!theirs.hasNext()
                || !Objects.equals(ours.next(), theirs.next())) {
                equal = false;
            }
        }
        if (equal && theirs.hasNext()) {
            equal = false;
        }
        return equal;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        final Iterator<X> iter = this.iterator();
        while (iter.hasNext()) {
            hash = 31 * hash + Objects.hashCode(iter.next());
        }
        return hash;
    }

    @Override
    public String toString() {
        return new ListOf<>(this).toString();
    }

    private ListIterator<X> joinedIterator() {
        return new JoinedListIterator<>(
            new ListOf<>(
                new Mapped<ListIterator<? extends X>>(
                    list -> list.listIterator(),
                    this.sources.value()
                )
            )
        );
    }
}
