/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;

/**
 * Func that is thread-safe and sticky.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.24
 */
public final class SolidFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param fnc Original function
     */
    public SolidFunc(final Func<X, Y> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Original function
     * @param max Max caching buffer length
     * @since 0.26
     */
    public SolidFunc(final Func<X, Y> fnc, final int max) {
        this.func = new SyncFunc<>(new StickyFunc<>(fnc, max));
    }

    @Override
    public Y apply(final X input) throws Exception {
        return this.func.apply(input);
    }
}
