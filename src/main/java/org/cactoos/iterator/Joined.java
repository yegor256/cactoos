/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Collections;
import java.util.Iterator;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * A few Iterators joined together.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.1
 */
public final class Joined<T> implements Iterator<T> {

    /**
     * Iterators, deferred.
     */
    private final Unchecked<Iterator<Iterator<? extends T>>> iters;

    /**
     * Current traversal iterator.
     */
    private Iterator<? extends T> current;

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    public Joined(final Iterator<? extends T>... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items Iterable
     * @since 0.49
     */
    @SuppressWarnings("unchecked")
    public Joined(final T item, final Iterator<? extends T> items) {
        this(new IterableOf<>(new IteratorOf<>(item), items));
    }

    /**
     * Ctor.
     * @param items Iterable
     * @param item End item
     * @since 0.49
     */
    @SuppressWarnings("unchecked")
    public Joined(final Iterator<? extends T> items, final T item) {
        this(new IterableOf<>(items, new IteratorOf<>(item)));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public Joined(final Iterable<Iterator<? extends T>> items) {
        this.iters = new Unchecked<>(new Sticky<>(items::iterator));
        this.current = null;
    }

    @Override
    public boolean hasNext() {
        if (this.current == null) {
            this.current = Collections.emptyIterator();
        }
        while (!this.current.hasNext() && this.iters.value().hasNext()) {
            this.current = this.iters.value().next();
        }
        return this.current.hasNext();
    }

    @Override
    public T next() {
        this.hasNext();
        return this.current.next();
    }
}
