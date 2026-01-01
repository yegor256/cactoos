/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * {@link Number} from {@link Scalar}s.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@SuppressFBWarnings({"SE_NO_SERIALVERSIONID", "SE_BAD_FIELD"})
public final class NumberOfScalars extends Number {

    /**
     * The LONG number.
     */
    private final Scalar<Long> lnum;

    /**
     * The INT number.
     */
    private final Scalar<Integer> inum;

    /**
     * The FLOAT number.
     */
    private final Scalar<Float> fnum;

    /**
     * The DOUBLE number.
     */
    private final Scalar<Double> dnum;

    /**
     * Ctor.
     * @param nbr Number
     */
    public NumberOfScalars(final Scalar<? extends Number> nbr) {
        this(
            () -> nbr.value().longValue(),
            () -> nbr.value().intValue(),
            () -> nbr.value().floatValue(),
            () -> nbr.value().doubleValue()
        );
    }

    /**
     * Ctor.
     * @param lnm Long scalar
     * @param inm Integer scalar
     * @param fnm Float scalar
     * @param dnm Long scalar
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public NumberOfScalars(final Scalar<Long> lnm, final Scalar<Integer> inm,
        final Scalar<Float> fnm, final Scalar<Double> dnm) {
        super();
        this.lnum = lnm;
        this.inum = inm;
        this.fnum = fnm;
        this.dnum = dnm;
    }

    @Override
    public int intValue() {
        return new Unchecked<>(this.inum).value();
    }

    @Override
    public long longValue() {
        return new Unchecked<>(this.lnum).value();
    }

    @Override
    public float floatValue() {
        return new Unchecked<>(this.fnum).value();
    }

    @Override
    public double doubleValue() {
        return new Unchecked<>(this.dnum).value();
    }

    @Override
    public String toString() {
        return Double.toString(this.doubleValue());
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Number && Double.compare(
            this.doubleValue(),
            ((Number) obj).doubleValue()
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.doubleValue());
    }
}
