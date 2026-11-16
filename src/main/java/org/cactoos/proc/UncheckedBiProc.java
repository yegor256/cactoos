/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.func.UncheckedBiFunc;

/**
 * BiProc that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.22
 */
public final class UncheckedBiProc<X, Y> implements BiProc<X, Y> {

    /**
     * Original proc.
     */
    private final BiProc<? super X, ? super Y> proc;

    /**
     * Ctor.
     *
     * @param prc Encapsulated proc
     */
    public UncheckedBiProc(final BiProc<? super X, ? super Y> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X first, final Y second) {
        new UncheckedBiFunc<X, Y, Void>(
            (fst, snd) -> {
                this.proc.exec(fst, snd);
                return null;
            }
        ).apply(first, second);
    }
}
