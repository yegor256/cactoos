/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Envelope for Scalar.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @since 0.41
 */
public abstract class ScalarEnvelope<T> implements Scalar<T> {

    /**
     * The delegate scalar.
     */
    private final Scalar<? extends T> scalar;

    /**
     * Ctor.
     * @param scalar The scalar
     */
    public ScalarEnvelope(final Scalar<? extends T> scalar) {
        this.scalar = scalar;
    }

    @Override
    public final T value() throws Exception {
        return this.scalar.value();
    }
}
