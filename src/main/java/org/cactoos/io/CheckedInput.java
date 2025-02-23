/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.scalar.Checked;

/**
 * Input that throws exception of specified type.
 *
 * @param <E> Exception's type.
 * @since 0.31
 */
public final class CheckedInput<E extends Exception> implements Input {

    /**
     * Original input.
     */
    private final Input origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<? super Exception, ? extends E> func;

    /**
     * Ctor.
     * @param orig Origin input.
     * @param fnc Function that wraps exceptions.
     */
    public CheckedInput(final Input orig, final Func<? super Exception, ? extends E> fnc) {
        this.origin = orig;
        this.func = fnc;
    }

    @Override
    public InputStream stream() throws E {
        return new Checked<>(
            this.origin::stream,
            this.func
        ).value();
    }
}
