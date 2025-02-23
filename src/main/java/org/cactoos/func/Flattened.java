/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * {@link Func} from {@link Func} of {@link Scalar}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.48
 */
public final class Flattened<X, Y> extends FuncEnvelope<X, Y> {
    /**
     * Ctor.
     * @param sclr The func
     */
    public Flattened(final Func<X, Scalar<Y>> sclr) {
        super(x -> sclr.apply(x).value());
    }
}
