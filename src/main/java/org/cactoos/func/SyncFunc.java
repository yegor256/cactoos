/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;

/**
 * Func that is thread-safe.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.4
 */
public final class SyncFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> func;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public SyncFunc(final Func<X, Y> fnc) {
        this(fnc, fnc);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param lck Sync lock
     */
    public SyncFunc(final Func<X, Y> fnc, final Object lck) {
        this.func = fnc;
        this.lock = lck;
    }

    @Override
    public Y apply(final X input) throws Exception {
        synchronized (this.lock) {
            return this.func.apply(input);
        }
    }
}
