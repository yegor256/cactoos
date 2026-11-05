/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * A few {@link ListIterator} joined together.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Items type
 * @since 1.0.0

 * <p>Mutations are delegated to the underlying iterators.
 * {@link #set(Object)} and {@link #remove()} act on the iterator that returned
 * the item last. {@link #add(Object)} inserts the item into the iterator
 * the cursor currently points at: when the cursor sits between two iterators,
 * the item goes to the end of the earlier one, unless {@link #hasNext()}
 * has already moved the cursor to the later one, and then it goes to
 * its beginning. Before any navigation the item goes to the first iterator.</p>
 */
public final class JoinedListIterator<T> implements ListIterator<T> {

    /**
     * {@link List} of {@link ListIterator}, deferred.
     */
    private final Unchecked<List<? extends ListIterator<? extends T>>> listiters;

    /**
     * Cursor of the {@link List} of {@link ListIterator}.
     */
    private final AtomicInteger cursorlit;

    /**
     * Cursor of {@link ListIterator}.
     */
    private final AtomicInteger cursor;

    /**
     * Position in the {@link List} of the iterator that returned the item last,
     * or -1 when there is no such item.
     */
    private final AtomicInteger last;

    /**
     * True when the item was returned by {@link #next()}, false when
     * by {@link #previous()}.
     */
    private final AtomicBoolean forward;

    /**
     * Ctor.
     *
     * @param items Items to concatenate
     */
    @SafeVarargs
    public JoinedListIterator(final ListIterator<? extends T>... items) {
        this(new ListOf<>(items));
    }

    /**
     * Ctor.
     *
     * @param item First item
     * @param items ListIterator
     */
    @SuppressWarnings("unchecked")
    public JoinedListIterator(final T item, final ListIterator<? extends T> items) {
        this(
            (Scalar<List<? extends ListIterator<? extends T>>>) () ->
                new ListOf<>(new ListOf<>(item).listIterator(), items)
        );
    }

    /**
     * Ctor.
     *
     * @param items ListIterators
     * @param item End item
     */
    @SuppressWarnings("unchecked")
    public JoinedListIterator(final ListIterator<? extends T> items, final T item) {
        this(
            (Scalar<List<? extends ListIterator<? extends T>>>) () ->
                new ListOf<>(items, new ListOf<>(item).listIterator())
        );
    }

    /**
     * Ctor.
     *
     * @param items Items to concatenate
     */
    public JoinedListIterator(final List<? extends ListIterator<? extends T>> items) {
        this((Scalar<List<? extends ListIterator<? extends T>>>) () -> items);
    }

    /**
     * Ctor.
     *
     * @param items Items to concatenate, deferred
     */
    private JoinedListIterator(
        final Scalar<List<? extends ListIterator<? extends T>>> items
    ) {
        this.listiters = new Unchecked<>(new Sticky<>(items));
        this.cursorlit = new AtomicInteger(-1);
        this.cursor = new AtomicInteger(-1);
        this.last = new AtomicInteger(-1);
        this.forward = new AtomicBoolean(true);
    }

    @Override
    public boolean hasNext() {
        while (!this.currentListIterator().hasNext() && this.listHasNextElt()) {
            this.cursorlit.getAndIncrement();
        }
        return this.currentListIterator().hasNext();
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return this.currentListIterator().next();
        } finally {
            this.cursor.getAndIncrement();
            this.last.set(this.cursorlit.get());
            this.forward.set(true);
        }
    }

    @Override
    public boolean hasPrevious() {
        while (!this.currentListIterator().hasPrevious() && this.listHasPreviousElt()) {
            this.cursorlit.getAndDecrement();
        }
        return this.currentListIterator().hasPrevious();
    }

    @Override
    public T previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        try {
            return this.currentListIterator().previous();
        } finally {
            this.cursor.getAndDecrement();
            this.last.set(this.cursorlit.get());
            this.forward.set(false);
        }
    }

    @Override
    public int nextIndex() {
        return this.cursor.get() + 1;
    }

    @Override
    public int previousIndex() {
        int previousidx = -1;
        if (this.hasPrevious()) {
            previousidx = this.cursor.get();
        }
        return previousidx;
    }

    @Override
    public void remove() {
        this.returning().remove();
        if (this.forward.get()) {
            this.cursor.getAndDecrement();
        }
        this.last.set(-1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(final T elt) {
        ((ListIterator<T>) this.returning()).set(elt);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(final T elt) {
        if (this.listiters.value().isEmpty()) {
            throw new UnsupportedOperationException(
                "There are no iterators to add an item to"
            );
        }
        if (this.cursorlit.get() == -1) {
            this.cursorlit.set(0);
        }
        ((ListIterator<T>) this.currentListIterator()).add(elt);
        this.cursor.getAndIncrement();
        this.last.set(-1);
    }

    private ListIterator<? extends T> returning() {
        if (this.last.get() == -1) {
            throw new IllegalStateException(
                "Neither next() nor previous() was called, or the item was already changed"
            );
        }
        return this.listiters.value().get(this.last.get());
    }

    private boolean listHasNextElt() {
        return this.cursorlit.get() + 1 <= this.listiters.value().size() - 1;
    }

    private boolean listHasPreviousElt() {
        return this.cursorlit.get() - 1 >= 0;
    }

    private ListIterator<? extends T> currentListIterator() {
        final ListIterator<? extends T> current;
        if (this.cursorlit.get() == -1) {
            current = Collections.emptyListIterator();
        } else {
            current = this.listiters.value().get(this.cursorlit.get());
        }
        return current;
    }
}
