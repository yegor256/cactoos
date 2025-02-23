/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;

/**
 * Envelope for Func.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.41
 */
public abstract class FuncEnvelope<X, Y> implements Func<X, Y> {

    /**
     * The delegate function.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param func The function
     */
    public FuncEnvelope(final Func<X, Y> func) {
        this.func = func;
    }

    @Override
    public final Y apply(final X input) throws Exception {
        return this.func.apply(input);
    }
}
