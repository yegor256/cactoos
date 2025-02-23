/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Proc;

/**
 * Envelope for {@link Proc}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.50
 */
public abstract class ProcEnvelope<X> implements Proc<X> {

    /**
     * Proc to decorate.
     */
    private final Proc<? super X> origin;

    /**
     * Ctor.
     * @param origin The procedure
     */
    public ProcEnvelope(final Proc<? super X> origin) {
        this.origin = origin;
    }

    @Override
    public final void exec(final X input) throws Exception {
        this.origin.exec(input);
    }
}
