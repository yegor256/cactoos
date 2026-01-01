/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Paged iterator.
 * Elements will continue to be provided so long as {@code next} produces
 * non-empty iterators.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.49
 */
public final class Paged<X> implements Iterator<X> {

    /**
     * Current element.
     */
    private final AtomicReference<Iterator<? extends X>> current;

    /**
     * Function to get the next element.
     */
    private final Func<? super Iterator<? extends X>, ? extends Iterator<? extends X>> subsequent;

    /**
     * Ctor.
     * @param first First element.
     * @param next Function to get the next element.
     */
    public Paged(
        final Iterator<? extends X> first,
        final Func<? super Iterator<? extends X>, ? extends Iterator<? extends X>> next
    ) {
        this.current = new AtomicReference<>(first);
        this.subsequent = next;
    }

    @Override
    public boolean hasNext() {
        if (!this.current.get().hasNext()) {
            final Iterator<? extends X> next = new UncheckedFunc<>(this.subsequent).apply(
                this.current.get()
            );
            this.current.set(next);
        }
        return this.current.get().hasNext();
    }

    @Override
    public X next() {
        if (this.hasNext()) {
            return this.current.get().next();
        }
        throw new NoSuchElementException();
    }
}
