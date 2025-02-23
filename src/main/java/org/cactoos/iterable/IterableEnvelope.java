/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;

/**
 * Iterable envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.24
 */
public abstract class IterableEnvelope<X> implements Iterable<X> {

    /**
     * The wrapped iterable.
     */
    private final Iterable<X> wrapped;

    /**
     * Ctor.
     * @param iterable The wrapped iterable
     */
    public IterableEnvelope(final Iterable<X> iterable) {
        this.wrapped = iterable;
    }

    @Override
    public final Iterator<X> iterator() {
        return this.wrapped.iterator();
    }

    @Override
    public final boolean equals(final Object other) {
        return this.wrapped.equals(other);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }
}
