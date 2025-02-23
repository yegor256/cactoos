/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;

/**
 * Envelope for BiProc.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.50
 */
public abstract class BiProcEnvelope<X, Y> implements BiProc<X, Y> {

    /**
     * BiProc to decorate.
     */
    private final BiProc<? super X, ? super Y> origin;

    /**
     * Ctor.
     * @param origin The procedure
     */
    public BiProcEnvelope(final BiProc<? super X, ? super Y> origin) {
        this.origin = origin;
    }

    @Override
    public final void exec(final X first, final Y second) throws Exception {
        this.origin.exec(first, second);
    }
}
