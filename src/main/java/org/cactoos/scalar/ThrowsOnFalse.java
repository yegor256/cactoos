/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Throws an exception on false expression.
 *
 * @since 0.56.0
 */
public final class ThrowsOnFalse implements Scalar<Boolean> {

    /**
     * Logical statement.
     */
    private final Scalar<Boolean> scalar;

    /**
     * Exception to throw.
     */
    private final Scalar<Exception> exception;

    /**
     * Ctor.
     * @param sclr Scalar
     * @param message Error Message
     */
    public ThrowsOnFalse(
        final Scalar<Boolean> sclr,
        final String message
    ) {
        this(
            sclr, () -> new IllegalArgumentException(message)
        );
    }

    /**
     * Ctor.
     * @param sclr Scalar
     * @param exc Exception
     */
    public ThrowsOnFalse(
        final Scalar<Boolean> sclr,
        final Scalar<Exception> exc
    ) {
        this.scalar = sclr;
        this.exception = exc;
    }

    @Override
    public Boolean value() throws Exception {
        if (!this.scalar.value()) {
            throw this.exception.value();
        }
        return true;
    }
}
