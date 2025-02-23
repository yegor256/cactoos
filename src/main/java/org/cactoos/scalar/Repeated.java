/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;

/**
 * Scalar that runs repeatedly for a number of times.
 *
 * @param <X> Type of output
 * @since 0.49.2
 */
public final class Repeated<X> implements Scalar<X> {

    /**
     * Scalar.
     */
    private final Scalar<? extends X> scalar;

    /**
     * Times to repeat.
     */
    private final int times;

    /**
     * Ctor.
     *
     * <p>If {@code max} is equal or less than zero {@link #value()} will return
     * an exception.</p>
     *
     * @param scalar Scalar to repeat.
     * @param times How many times.
     */
    public Repeated(final Scalar<? extends X> scalar, final int times) {
        this.scalar = scalar;
        this.times = times;
    }

    @Override
    public X value() throws Exception {
        return new org.cactoos.func.Repeated<>(
            new FuncOf<>(this.scalar),
            this.times
        ).apply(true);
    }
}
