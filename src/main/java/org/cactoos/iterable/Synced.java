/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;

/**
 * Synchronized iterable.
 *
 * <p>
 * This class should be used very carefully. You must understand that it will
 * fetch the entire content of the encapsulated {@link Iterable} on each method
 * call. It doesn't cache the data anyhow. If you don't need this
 * {@link Iterable} to re-fresh its content on every call, by doing round-trips
 * to the encapsulated iterable, use {@link Sticky}.
 * </p>
 *
 * <p>
 * Objects of this class are thread-safe.
 * </p>
 * @param <X> Type of item
 * @since 0.24
 */
public final class Synced<X> implements Iterable<X> {

    /**
     * The iterable.
     */
    private final Iterable<? extends X> origin;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Synced(final X... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param iterable The iterable synchronize access to.
     */
    public Synced(final Iterable<? extends X> iterable) {
        this(new Object(), iterable);
    }

    /**
     * Ctor.
     * @param lck The lock to synchronize with.
     * @param iterable The iterable synchronize access to.
     */
    public Synced(final Object lck, final Iterable<? extends X> iterable) {
        this.origin = iterable;
        this.lock = lck;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<X> iterator() {
        synchronized (this.lock) {
            return (Iterator<X>) this.origin.iterator();
        }
    }
}
