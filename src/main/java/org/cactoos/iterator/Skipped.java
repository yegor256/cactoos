/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;

/**
 * Skipped iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.34
 */
public final class Skipped<T> implements Iterator<T> {

    /**
     * Sliced iterator.
     */
    private final Iterator<? extends T> sliced;

    /**
     * Ctor.
     * @param skp Count skip elements
     * @param iterator Decorated iterator
     */
    public Skipped(final int skp, final Iterator<? extends T> iterator) {
        this.sliced = new Sliced<>(skp, iterator);
    }

    @Override
    public boolean hasNext() {
        return this.sliced.hasNext();
    }

    @Override
    public T next() {
        return this.sliced.next();
    }
}
