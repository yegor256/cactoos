/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;

/**
 * BiFunc that is thread-safe.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @param <X> Type of first input
 * @param <Y> Type of second input
 * @param <Z> Type of output
 * @since 0.18
 */
public final class SyncBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public SyncBiFunc(final BiFunc<X, Y, Z> fnc) {
        this(fnc, fnc);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param lck Sync lock
     */
    public SyncBiFunc(final BiFunc<X, Y, Z> fnc, final Object lck) {
        this.func = fnc;
        this.lock = lck;
    }

    @Override
    public Z apply(final X first, final Y second) throws Exception {
        synchronized (this.lock) {
            return this.func.apply(first, second);
        }
    }
}
