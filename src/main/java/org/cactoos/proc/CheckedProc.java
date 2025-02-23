/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.scalar.Checked;

/**
 * Proc that throws exception of specified type.
 *
 * @param <X> Type of input
 * @param <E> Exception's type
 * @since 0.32
 */
public final class CheckedProc<X, E extends Exception> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<Exception, E> func;

    /**
     * Ctor.
     * @param original Original proc
     * @param fnc Function that wraps exceptions.
     */
    public CheckedProc(final Proc<X> original, final Func<Exception, E> fnc) {
        this.origin = original;
        this.func = fnc;
    }

    @Override
    public void exec(final X input) throws E {
        new Checked<>(
            () -> {
                this.origin.exec(input);
                return true;
            },
            this.func
        ).value();
    }
}
