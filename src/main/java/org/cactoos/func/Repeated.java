/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;

/**
 * Func that repeats its calculation a few times before
 * returning the last result.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.6
 */
public final class Repeated<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<? super X, ? extends Y> func;

    /**
     * How many times to run.
     */
    private final int times;

    /**
     * Ctor.
     *
     * <p>If {@code max} is equal or less than zero {@link #apply(Object)}
     * will return an exception.</p>
     *
     * @param fnc Func original
     * @param max How many times
     */
    public Repeated(final Func<? super X, ? extends Y> fnc, final int max) {
        this.func = fnc;
        this.times = max;
    }

    @Override
    public Y apply(final X input) throws Exception {
        if (this.times <= 0) {
            throw new IllegalArgumentException(
                "The number of repetitions must be at least 1"
            );
        }
        Y result = null;
        for (int idx = 0; idx < this.times; ++idx) {
            result = this.func.apply(input);
        }
        return result;
    }

}
