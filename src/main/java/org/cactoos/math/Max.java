/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.math;

import org.cactoos.Scalar;

/**
 * Find the greater among numbers.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.7
 */
@SuppressWarnings("serial")
public final class Max extends Number implements Scalar<Number> {

    /**
     * Numbers.
     */
    private final Number[] numbers;

    /**
     * Ctor.
     * @param numbers The numbers
     */
    public Max(final Number... numbers) {
        super();
        this.numbers = numbers;
    }

    @Override
    public Number asValue() throws Exception {
        Number max = this.numbers[0];
        for (int index = 1; index < this.numbers.length; ++index) {
            if (this.numbers[index].doubleValue() > max.doubleValue()) {
                max = this.numbers[index];
            }
        }
        return max;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public int intValue() {
        try {
            return this.asValue().intValue();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public long longValue() {
        try {
            return this.asValue().longValue();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public float floatValue() {
        try {
            return this.asValue().floatValue();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public double doubleValue() {
        try {
            return this.asValue().doubleValue();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

}
