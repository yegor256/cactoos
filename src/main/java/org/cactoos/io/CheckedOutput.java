/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Func;
import org.cactoos.Output;
import org.cactoos.scalar.Checked;

/**
 * Output that throws exception of specified type.
 *
 * @param <E> Exception's type.
 * @since 0.31
 */
public final class CheckedOutput<E extends Exception> implements Output {

    /**
     * Original output.
     */
    private final Output origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<? super Exception, ? extends E> func;

    /**
     * Ctor.
     * @param orig Origin output.
     * @param fnc Function that wraps exceptions.
     */
    public CheckedOutput(final Output orig, final Func<? super Exception, ? extends E> fnc) {
        this.origin = orig;
        this.func = fnc;
    }

    @Override
    public OutputStream stream() throws E {
        return new Checked<>(
            this.origin::stream,
            this.func
        ).value();
    }
}
