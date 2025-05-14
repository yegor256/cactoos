/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;

/**
 * Envelope of {@link BiFunc}.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.50
 */
public abstract class BiFuncEnvelope<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * BiFunc to decorate.
     */
    private final BiFunc<X, Y, Z> origin;

    /**
     * Ctor.
     * @param func The function
     */
    public BiFuncEnvelope(final BiFunc<X, Y, Z> func) {
        this.origin = func;
    }

    @Override
    public final Z apply(final X first, final Y second) throws Exception {
        return this.origin.apply(first, second);
    }
}
