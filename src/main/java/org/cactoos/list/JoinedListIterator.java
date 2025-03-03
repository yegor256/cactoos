/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A few {@link ListIterator} joined together.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Items type
 * @since 1.0.0
 * @todo #1254:30min We should implement mutable operations (#add, #set and #remove)
 *  according to their javadoc. Also, the behaviour of adding/setting/removing an element
 *  when the cursor is in between two listerators should be well documented in this
 *  class (because there are multiple options in that case).
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class JoinedListIterator<T> implements ListIterator<T> {

    /**
     * {@link List} of {@link ListIterator}.
     */
    private final List<? extends ListIterator<? extends T>> listiters;

    /**
     * Cursor of the {@link List} of {@link ListIterator}.
     */
    private final AtomicInteger cursorlit;

    /**
     * Cursor of {@link ListIterator}.
     */
    private final AtomicInteger cursor;

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    public JoinedListIterator(final ListIterator<? extends T>... items) {
        this(new ListOf<>(items));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items ListIterator
     */
    @SuppressWarnings("unchecked")
    public JoinedListIterator(final T item, final ListIterator<? extends T> items) {
        this(new ListOf<>(new ListOf<>(item).listIterator(), items));
    }

    /**
     * Ctor.
     * @param items ListIterators
     * @param item End item
     */
    @SuppressWarnings("unchecked")
    public JoinedListIterator(final ListIterator<? extends T> items, final T item) {
        this(new ListOf<>(items, new ListOf<>(item).listIterator()));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public JoinedListIterator(final List<? extends ListIterator<? extends T>> items) {
        this.listiters = items;
        this.cursorlit = new AtomicInteger(-1);
        this.cursor = new AtomicInteger(-1);
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
        final T next = this.currentListIterator().next();
        this.cursor.getAndIncrement();
        return next;
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
        final T previous = this.currentListIterator().previous();
        this.cursor.getAndDecrement();
        return previous;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(final T elt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final T elt) {
        throw new UnsupportedOperationException();
    }

    /**
     * If {@link List} of {@link ListIterator} has next element.
     * @return Has or no
     */
    private boolean listHasNextElt() {
        return this.cursorlit.get() + 1 <= this.listiters.size() - 1;
    }

    /**
     * If {@link List} of {@link ListIterator} has previous element.
     * @return Has or no
     */
    private boolean listHasPreviousElt() {
        return this.cursorlit.get() - 1 >= 0;
    }

    /**
     * Get current {@link ListIterator}.
     * @return Current element
     */
    private ListIterator<? extends T> currentListIterator() {
        final ListIterator<? extends T> current;
        if (this.cursorlit.get() == -1) {
            current = Collections.emptyListIterator();
        } else {
            current = this.listiters.get(this.cursorlit.get());
        }
        return current;
    }
}
