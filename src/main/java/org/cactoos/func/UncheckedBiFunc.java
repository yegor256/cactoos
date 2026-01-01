/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.scalar.Unchecked;

/**
 * BiFunc that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.13
 */
public final class UncheckedBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public UncheckedBiFunc(final BiFunc<X, Y, Z> fnc) {
        this.func = fnc;
    }

    @Override
    public Z apply(final X first, final Y second) {
        return new Unchecked<>(
            () -> this.func.apply(first, second)
        ).value();
    }
}
