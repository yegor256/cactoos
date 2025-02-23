/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.Collection;
import org.cactoos.iterable.IterableEnvelope;

/**
 * Base collection.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @param <X> Element type
 * @since 0.23
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class CollectionEnvelope<X>
    extends IterableEnvelope<X> implements Collection<X> {

    /**
     * The wrapped collection.
     */
    private final Collection<X> col;

    /**
     * Ctor.
     * @param col The wrapped collection
     */
    public CollectionEnvelope(final Collection<X> col) {
        super(col);
        this.col = col;
    }

    @Override
    public final int size() {
        return this.col.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.col.isEmpty();
    }

    @Override
    public final boolean contains(final Object object) {
        return this.col.contains(object);
    }

    @Override
    public final Object[] toArray() {
        return this.col.toArray();
    }

    @Override
    public final <T> T[] toArray(final T[] array) {
        return this.col.toArray(array);
    }

    @Override
    public final boolean add(final X item) {
        return this.col.add(item);
    }

    @Override
    public final boolean remove(final Object object) {
        return this.col.remove(object);
    }

    @Override
    public final boolean containsAll(final Collection<?> list) {
        return this.col.containsAll(list);
    }

    @Override
    public final boolean addAll(final Collection<? extends X> list) {
        return this.col.addAll(list);
    }

    @Override
    public final boolean removeAll(final Collection<?> list) {
        return this.col.removeAll(list);
    }

    @Override
    public final boolean retainAll(final Collection<?> list) {
        return this.col.retainAll(list);
    }

    @Override
    public final void clear() {
        this.col.clear();
    }
}
