/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * {@link Scalar} from {@link Scalar} of {@link Scalar}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Element type
 * @since 0.48
 */
public final class Flattened<X> extends ScalarEnvelope<X> {
    /**
     * Ctor.
     * @param sclr The func
     */
    public Flattened(final Scalar<? extends Scalar<? extends X>> sclr) {
        super(() -> sclr.value().value());
    }
}
