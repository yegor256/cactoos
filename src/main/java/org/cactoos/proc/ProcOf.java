/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func as Proc.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.12
 */
public final class ProcOf<X> extends ProcEnvelope<X> {

    /**
     * Ctor.
     * @param fnc The proc
     */
    public ProcOf(final Func<? super X, ?> fnc) {
        this(
            input -> {
                fnc.apply(input);
            }
        );
    }

    /**
     * Ctor.
     * @param prc The proc
     */
    public ProcOf(final Proc<? super X> prc) {
        super(prc);
    }
}
