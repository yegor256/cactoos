/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Scalar check for no nulls.
 *
 * @param <T> Type of result
 * @since 0.11
 */
public final class NoNulls<T> implements Scalar<T> {
    /**
     * The scalar.
     */
    private final Scalar<? extends T> origin;

    /**
     * Ctor.
     * @param sclr The scalar
     */
    public NoNulls(final Scalar<? extends T> sclr) {
        this.origin = sclr;
    }

    @Override
    public T value() throws Exception {
        if (this.origin == null) {
            throw new IllegalArgumentException(
                "NULL instead of a valid scalar"
            );
        }
        final T value = this.origin.value();
        if (value == null) {
            throw new IllegalStateException(
                "NULL instead of a valid value"
            );
        }
        return value;
    }
}
