/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;

/**
 * BiFunc check for no nulls.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.11
 */
public final class BiFuncNoNulls<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * The function.
     */
    private final BiFunc<X, Y, Z> origin;

    /**
     * Ctor.
     * @param func The function
     */
    public BiFuncNoNulls(final BiFunc<X, Y, Z> func) {
        this.origin = func;
    }

    @Override
    public Z apply(final X first, final Y second) throws Exception {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid function"
            );
        }
        if (first == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid first argument"
            );
        }
        if (second == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid second argument"
            );
        }
        final Z result = this.origin.apply(first, second);
        if (result == null) {
            throw new IllegalStateException(
                "NULL instead of a valid result"
            );
        }
        return result;
    }
}
