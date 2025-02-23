/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.Func;

/**
 * Func check for no nulls.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.10
 */
public final class FuncNoNulls<X, Y> implements Func<X, Y> {
    /**
     * The function.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param fnc The function
     */
    public FuncNoNulls(final Func<X, Y> fnc) {
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) throws Exception {
        if (this.func == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid function"
            );
        }
        if (input == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid input"
            );
        }
        final Y result = this.func.apply(input);
        if (result == null) {
            throw new IOException("NULL instead of a valid result");
        }
        return result;
    }
}
