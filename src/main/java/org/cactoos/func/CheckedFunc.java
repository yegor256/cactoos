/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.scalar.Checked;

/**
 * Func that throws exception of specified type.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @param <E> Exception's type
 * @since 0.32
 */
public final class CheckedFunc<X, Y, E extends Exception> implements
    Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param original Original func
     * @param fnc Function that wraps exceptions.
     */
    public CheckedFunc(final Func<X, Y> original,
        final Func<Exception, E> fnc) {
        this.origin = original;
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) throws E {
        return new Checked<>(
            () -> this.origin.apply(input),
            this.func
        ).value();
    }
}
