/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Sorted iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.7
 */
public final class Sorted<T> implements Iterator<T> {

    /**
     * Sorted one.
     */
    private final Unchecked<Iterator<T>> scalar;

    /**
     * Ctor.
     *
     * <p>If you're using this ctor you must be sure that type {@code T}
     * implements {@link Comparable} interface. Otherwise, there will be
     * a type casting exception in runtime.</p>
     *
     * @param items The underlying iterator
     */
    @SuppressWarnings("unchecked")
    public Sorted(final Iterator<? extends T> items) {
        this((Comparator<T>) Comparator.naturalOrder(), items);
    }

    /**
     * Ctor.
     * @param comparator The comparator
     * @param iterator The underlying iterator
     */
    public Sorted(final Comparator<? super T> comparator, final Iterator<? extends T> iterator) {
        this.scalar = new Unchecked<>(
            new Sticky<>(
                () -> {
                    final List<T> items = new LinkedList<>();
                    while (iterator.hasNext()) {
                        items.add(iterator.next());
                    }
                    items.sort(comparator);
                    return items.iterator();
                }
            )
        );
    }

    @Override
    public boolean hasNext() {
        return this.scalar.value().hasNext();
    }

    @Override
    public T next() {
        return this.scalar.value().next();
    }
}
