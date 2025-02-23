/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Cached and synchronized version of a Scalar.
 *
 * <p>Objects of this class are thread safe.
 *
 * @param <T> Type of result
 * @see Sticky
 * @see Synced
 * @since 0.24
 */
public final class Solid<T> implements Scalar<T> {

    /**
     * Origin.
     */
    private final Scalar<? extends T> origin;

    /**
     * Cache.
     */
    private volatile T cache;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param origin The Scalar to cache and sync
     */
    public Solid(final Scalar<? extends T> origin) {
        this.origin = origin;
        this.lock = new Object();
    }

    @Override
    public T value() throws Exception {
        if (this.cache == null) {
            synchronized (this.lock) {
                if (this.cache == null) {
                    this.cache = this.origin.value();
                }
            }
        }
        return this.cache;
    }
}
