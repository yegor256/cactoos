/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.cactoos.scalar.Checked;

/**
 * BiFunc that throws exception of specified type.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @param <E> Exception's type
 * @since 0.32
 */
public final class CheckedBiFunc<X, Y, Z, E extends Exception> implements
    BiFunc<X, Y, Z> {

    /**
     * Original BiFunc.
     */
    private final BiFunc<X, Y, Z> origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param original Original BiFunc
     * @param fnc Function that wraps exceptions.
     */
    public CheckedBiFunc(final BiFunc<X, Y, Z> original,
        final Func<Exception, E> fnc) {
        this.origin = original;
        this.func = fnc;
    }

    @Override
    public Z apply(final X first, final Y second) throws E {
        return new Checked<>(
            () -> this.origin.apply(first, second),
            this.func
        ).value();
    }
}
