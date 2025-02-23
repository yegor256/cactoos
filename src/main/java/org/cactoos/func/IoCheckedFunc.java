/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.scalar.IoChecked;

/**
 * Func that doesn't throw checked {@link Exception}, but throws
 * {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.4
 */
public final class IoCheckedFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<? super X, ? extends Y> func;

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public IoCheckedFunc(final Func<? super X, ? extends Y> fnc) {
        this.func = fnc;
    }

    @Override
    public Y apply(final X input) throws IOException {
        return new IoChecked<>(() -> this.func.apply(input)).value();
    }

}
