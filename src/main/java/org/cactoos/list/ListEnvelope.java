/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.collection.CollectionEnvelope;

/**
 * {@link List} envelope.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.23
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public abstract class ListEnvelope<T> extends CollectionEnvelope<T> implements
    List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param list Encapsulated list
     */
    public ListEnvelope(final List<T> list) {
        super(list);
        this.list = list;
    }

    @Override
    public final boolean addAll(final int index,
        final Collection<? extends T> items) {
        return this.list.addAll(index, items);
    }

    @Override
    public final T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public final T set(final int index, final T element) {
        return this.list.set(index, element);
    }

    @Override
    public final void add(final int index, final T element) {
        this.list.add(index, element);
    }

    @Override
    public final T remove(final int index) {
        return this.list.remove(index);
    }

    @Override
    public final int indexOf(final Object item) {
        return this.list.indexOf(item);
    }

    @Override
    public final int lastIndexOf(final Object item) {
        return this.list.lastIndexOf(item);
    }

    @Override
    public final ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public final ListIterator<T> listIterator(final int index) {
        return this.list.listIterator(index);
    }

    @Override
    public final List<T> subList(final int start, final int end) {
        return this.list.subList(start, end);
    }
}
