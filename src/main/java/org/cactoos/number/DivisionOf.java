/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.math.BigDecimal;
import org.cactoos.Scalar;

/**
 * Division result of two numbers.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class DivisionOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -7835189568890281261L;

    /**
     * Ctor.
     * @param dividend The dividend
     * @param divisor The divisor
     */
    public DivisionOf(final BigDecimal dividend, final BigDecimal divisor) {
        this(() -> dividend.divide(divisor));
    }

    /**
     * Ctor.
     * @param dividend The dividend
     * @param divisor The divisor
     */
    public DivisionOf(final Number dividend, final Number divisor) {
        this(
            () -> new BigDecimal(dividend.toString()).divide(
                new BigDecimal(divisor.toString())
            )
        );
    }

    /**
     * Ctor.
     * @param result The result
     */
    private DivisionOf(final Scalar<BigDecimal> result) {
        super(new NumberOfScalars(result));
    }
}
