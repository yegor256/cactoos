/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.scalar.Checked;

/**
 * BiProc that throws exception of specified type.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <E> Exception's type
 * @since 0.32
 */
public final class CheckedBiProc<X, Y, E extends Exception> implements
    BiProc<X, Y> {

    /**
     * Original BiProc.
     */
    private final BiProc<X, Y> origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param original Original BiProc
     * @param fnc Function that wraps exceptions.
     */
    public CheckedBiProc(final BiProc<X, Y> original,
        final Func<Exception, E> fnc) {
        this.origin = original;
        this.func = fnc;
    }

    @Override
    public void exec(final X first, final Y second) throws E {
        new Checked<>(
            () -> {
                this.origin.exec(first, second);
                return true;
            },
            this.func
        ).value();
    }
}
