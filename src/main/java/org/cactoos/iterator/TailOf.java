/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Reversed;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Tail portion of the iterator.
 *
 * <p>
 * There is no thread-safety guarantee.
 * </p>
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> implements Iterator<T> {

    /**
     * The wrapped iterator, deferred.
     */
    private final Unchecked<Iterator<? extends T>> wrapped;

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param iterator Decorated iterator
     */
    public TailOf(final int num, final Iterator<? extends T> iterator) {
        this((Scalar<Iterator<? extends T>>) () -> new Reversed<>(
            new HeadOf<>(
                num,
                new Reversed<>(
                    new IterableOf<>(iterator)
                )
            )
        ).iterator());
    }

    /**
     * Ctor.
     * @param iter The iterator, deferred
     */
    private TailOf(final Scalar<Iterator<? extends T>> iter) {
        this.wrapped = new Unchecked<>(new Sticky<>(iter));
    }

    @Override
    public boolean hasNext() {
        return this.wrapped.value().hasNext();
    }

    @Override
    public T next() {
        return this.wrapped.value().next();
    }
}
