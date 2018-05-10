/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Envelope for the {@link Number}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.26
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class NumberEnvelope extends Number implements Scalar<Double> {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -1924406337256921883L;

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
     * @param dnm Double scalar
     */
    public NumberEnvelope(final Scalar<Double> dnm) {
        this(
            () -> dnm.value().longValue(),
            () -> dnm.value().intValue(),
            () -> dnm.value().floatValue(),
            dnm
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
    public NumberEnvelope(final Scalar<Long> lnm, final Scalar<Integer> inm,
        final Scalar<Float> fnm, final Scalar<Double> dnm) {
        super();
        this.lnum = lnm;
        this.inum = inm;
        this.fnum = fnm;
        this.dnum = dnm;
    }

    @Override
    public final int intValue() {
        return new UncheckedScalar<>(this.inum).value();
    }

    @Override
    public final long longValue() {
        return new UncheckedScalar<>(this.lnum).value();
    }

    @Override
    public final float floatValue() {
        return new UncheckedScalar<>(this.fnum).value();
    }

    @Override
    public final double doubleValue() {
        return new UncheckedScalar<>(this.dnum).value();
    }

    @Override
    public final Double value() throws Exception {
        return this.dnum.value();
    }
}
