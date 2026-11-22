/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;

/**
 * Composed function.
 *
 * @param <X> Type of input
 * @param <Y> Intermediate type
 * @param <Z> Type of output
 * @since 0.7
 */
public final class Chained<X, Y, Z> implements Func<X, Z> {

    /**
     * Before function.
     */
    private final Func<? super X, ? extends Y> before;

    /**
     * Functions.
     */
    private final Iterable<? extends Func<? super Y, ? extends Y>> funcs;

    /**
     * After function.
     */
    private final Func<? super Y, ? extends Z> after;

    /**
     * Ctor.
     *
     * @param bfr Before function
     * @param atr After function
     */
    public Chained(final Func<? super X, ? extends Y> bfr, final Func<? super Y, ? extends Z> atr) {
        this(bfr, new IterableOf<>(), atr);
    }

    /**
     * Ctor.
     *
     * @param bfr Before function
     * @param list Functions
     * @param atr After function
     */
    public Chained(
        final Func<? super X, ? extends Y> bfr,
        final Iterable<? extends Func<? super Y, ? extends Y>> list,
        final Func<? super Y, ? extends Z> atr
    ) {
        this.before = bfr;
        this.funcs = list;
        this.after = atr;
    }

    @Override
    public Z apply(final X input) throws Exception {
        Y temp = this.before.apply(input);
        for (final Func<? super Y, ? extends Y> func : this.funcs) {
            temp = func.apply(temp);
        }
        return this.after.apply(temp);
    }
}
