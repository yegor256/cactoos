/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.scalar.Unchecked;

/**
 * Func that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.2
 */
public final class UncheckedFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public UncheckedFunc(final Func<X, Y> fnc) {
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) {
        return new Unchecked<>(
            () -> this.func.apply(input)
        ).value();
    }

}
