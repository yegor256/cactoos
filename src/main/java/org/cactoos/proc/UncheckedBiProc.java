/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.func.BiFuncOf;
import org.cactoos.func.UncheckedBiFunc;

/**
 * BiProc that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.22
 */
public final class UncheckedBiProc<X, Y> implements BiProc<X, Y> {

    /**
     * Original proc.
     */
    private final BiProc<X, Y> proc;

    /**
     * Ctor.
     * @param prc Encapsulated proc
     */
    public UncheckedBiProc(final BiProc<X, Y> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X first, final Y second) {
        new UncheckedBiFunc<>(
            new BiFuncOf<>(this.proc, null)
        ).apply(first, second);
    }
}
