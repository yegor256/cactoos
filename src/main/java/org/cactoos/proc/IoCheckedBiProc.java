/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import org.cactoos.BiProc;

/**
 * BiProc that doesn't throw checked {@link Exception}, but throws
 * {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.22
 */
public final class IoCheckedBiProc<X, Y> implements BiProc<X, Y> {

    /**
     * Original proc.
     */
    private final BiProc<X, Y> proc;

    /**
     * Ctor.
     * @param prc Encapsulated func
     */
    public IoCheckedBiProc(final BiProc<X, Y> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X first, final Y second) throws IOException {
        new CheckedBiProc<>(
            this.proc,
            IOException::new
        ).exec(first, second);
    }

}
