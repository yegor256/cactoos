/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.cactoos.scalar.Sticky;

/**
 * Func that caches previously calculated values and doesn't
 * recalculate again.
 *
 * <p>This {@link Func} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncFunc}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @see Sticky
 * @since 0.1
 */
public final class StickyFunc<X, Y> implements Func<X, Y> {

    /**
     * Sticky bi-func.
     */
    private final BiFunc<X, Boolean, Y> func;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public StickyFunc(final Func<X, Y> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param max Maximum cache size
     * @since 0.26
     */
    public StickyFunc(final Func<X, Y> fnc, final int max) {
        this.func = new StickyBiFunc<>(
            (first, second) -> fnc.apply(first),
            max
        );
    }

    @Override
    public Y apply(final X input) throws Exception {
        return this.func.apply(input, true);
    }

}
