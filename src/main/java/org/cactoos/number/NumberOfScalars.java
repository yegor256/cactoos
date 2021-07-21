/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
