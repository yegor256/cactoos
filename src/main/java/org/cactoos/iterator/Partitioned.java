/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.cactoos.list.Immutable;
import org.cactoos.list.ListOf;

/**
 * Iterator implementation for {@link Iterator} partitioning.
 *
 * @param <T> Partitions value type
 * @since 0.29
 */
public final class Partitioned<T> implements Iterator<List<T>> {

    /**
     * Iterator to decorate.
     */
    private final Iterator<? extends T> decorated;

    /**
     * Size of the partitions.
     */
    private final int size;

    /**
     * Ctor.
     *
     * @param sze Size of the partitions.
     * @param src Source iterator.
     */
    public Partitioned(final int sze, final Iterator<? extends T> src) {
        this.size = sze;
        this.decorated = src;
    }

    @Override
    public boolean hasNext() {
        return this.decorated.hasNext();
    }

    @Override
    public List<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No partition left.");
        }
        if (this.size < 1) {
            throw new IllegalArgumentException("Partition size < 1");
        }
        return new Immutable<>(
            new ListOf<>(new Sliced<>(0, this.size, this.decorated))
        );
    }

}
