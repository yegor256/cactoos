/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.FuncOf;
import org.cactoos.func.Repeated;

/**
 * Proc that runs repeatedly for a number of times.
 *
 * @param <X> Type of input
 * @since 0.49.2
 */
public final class RepeatedProc<X> implements Proc<X> {
    /**
     * The repeated func.
     */
    private final Func<? super X, Boolean> func;

    /**
     * Ctor.
     *
     * <p>If {@code count} is equal or less than zero {@link #exec(Object)}
     * will return an exception.</p>
     *
     * @param prc Proc to repeat.
     * @param count How many times.
     */
    public RepeatedProc(final Proc<? super X> prc, final int count) {
        this.func = new Repeated<>(
            new FuncOf<>(prc, true),
            count
        );
    }

    @Override
    public void exec(final X input) throws Exception {
        this.func.apply(input);
    }
}
