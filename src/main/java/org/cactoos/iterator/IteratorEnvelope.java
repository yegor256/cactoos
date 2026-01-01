/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * {@link Iterator} that delegates to another {@link Iterator}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of item
 * @since 0.43
 */
public abstract class IteratorEnvelope<X> implements Iterator<X> {

    /**
     * Wrapped {@link Iterator}.
     */
    private final Iterator<? extends X> wrapped;

    /**
     * Ctor.
     * @param iter The {@link Iterator} to wrap.
     */
    public IteratorEnvelope(final Iterator<? extends X> iter) {
        this.wrapped = iter;
    }

    @Override
    public final boolean hasNext() {
        return this.wrapped.hasNext();
    }

    @Override
    public final X next() {
        return this.wrapped.next();
    }

    @Override
    public final void forEachRemaining(final Consumer<? super X> action) {
        this.wrapped.forEachRemaining(action);
    }

    @Override
    public final void remove() {
        this.wrapped.remove();
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.wrapped.equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }
}
