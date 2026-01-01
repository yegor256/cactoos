/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.Collections;
import org.cactoos.Func;

/**
 * Composed function.
 *
 * @param <X> Type of input.
 * @param <Y> Intermediate type.
 * @param <Z> Type of output.
 * @since 0.7
 */
public final class Chained<X, Y, Z> implements Func<X, Z> {

    /**
     * Before function.
     */
    private final Func<X, Y> before;

    /**
     * Functions.
     */
    private final Iterable<Func<Y, Y>> funcs;

    /**
     * After function.
     */
    private final Func<Y, Z> after;

    /**
     * Ctor.
     * @param bfr Before function
     * @param list Functions
     * @param atr After function
     */
    public Chained(final Func<X, Y> bfr, final Iterable<Func<Y, Y>> list,
        final Func<Y, Z> atr) {
        this.before = bfr;
        this.funcs = list;
        this.after = atr;
    }

    /**
     * Ctor.
     * @param bfr Before function
     * @param atr After function
     */
    public Chained(final Func<X, Y> bfr, final Func<Y, Z> atr) {
        this(bfr, Collections.emptyList(), atr);
    }

    @Override
    public Z apply(final X input) throws Exception {
        Y temp = this.before.apply(input);
        for (final Func<Y, Y> func : this.funcs) {
            temp = func.apply(temp);
        }
        return this.after.apply(temp);
    }
}
