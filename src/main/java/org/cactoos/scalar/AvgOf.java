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
package org.cactoos.scalar;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Average of numbers.
 *
 * <p>Here is how you can use it to fine mathematical average of numbers:</p>
 *
 * <pre>
 * int sum = new AvgOf(1, 2, 3, 4).intValue();
 * long sum = new AvgOf(1L, 2L, 3L).longValue();
 * int sum = new AvgOf(numbers.toArray(new Integer[numbers.size()])).intValue();
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.24
 * @checkstyle ExecutableStatementCountCheck (500 lines)
 * @checkstyle NPathComplexityCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.CallSuperInConstructor",
        "PMD.OnlyOneConstructorShouldDoInitialization",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
    }
)
public final class AvgOf extends Number {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -1924406337256921883L;

    /**
     * The LONG sum.
     */
    private final Scalar<Long> lsum;

    /**
     * The INT sum.
     */
    private final Scalar<Integer> isum;

    /**
     * The FLOAT sum.
     */
    private final Scalar<Float> fsum;

    /**
     * The DOUBLE sum.
     */
    private final Scalar<Double> dsum;

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Integer... src) {
        this(
            () -> {
                int sum = 0;
                int total = 0;
                for (final int val : src) {
                    sum += val;
                    ++total;
                }
                if (total == 0) {
                    total = 1;
                }
                return sum / total;
            },
            () -> {
                long sum = 0L;
                long total = 0L;
                for (final int val : src) {
                    sum += (long) val;
                    ++total;
                }
                if (total == 0L) {
                    total = 1L;
                }
                return sum / total;
            },
            () -> {
                double sum = 0.0d;
                double total = 0.0d;
                for (final int val : src) {
                    sum += (double) val;
                    total += 1.0d;
                }
                if (total == 0.0d) {
                    total = 1.0d;
                }
                return sum / total;
            },
            () -> {
                float sum = 0.0f;
                float total = 0.0f;
                for (final int val : src) {
                    sum += val;
                    total += 1.0f;
                }
                if (total == 0.0f) {
                    total = 1.0f;
                }
                return sum / total;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Long... src) {
        this(
            () -> {
                int sum = 0;
                int total = 0;
                for (final long val : src) {
                    sum += (int) val;
                    ++total;
                }
                if (total == 0) {
                    total = 1;
                }
                return sum / total;
            },
            () -> {
                long sum = 0L;
                long total = 0L;
                for (final long val : src) {
                    sum += val;
                    ++total;
                }
                if (total == 0L) {
                    total = 1L;
                }
                return sum / total;
            },
            () -> {
                double sum = 0.0d;
                double total = 0.0d;
                for (final long val : src) {
                    sum += (double) val;
                    total += 1.0d;
                }
                if (total == 0.0d) {
                    total = 1.0d;
                }
                return sum / total;
            },
            () -> {
                float sum = 0.0f;
                float total = 0.0f;
                for (final long val : src) {
                    sum += (float) val;
                    total += 1.0f;
                }
                if (total == 0.0f) {
                    total = 1.0f;
                }
                return sum / total;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Double... src) {
        this(
            () -> {
                int sum = 0;
                int total = 0;
                for (final double val : src) {
                    sum += (int) val;
                    ++total;
                }
                if (total == 0) {
                    total = 1;
                }
                return sum / total;
            },
            () -> {
                long sum = 0L;
                long total = 0L;
                for (final double val : src) {
                    sum += (long) val;
                    ++total;
                }
                if (total == 0L) {
                    total = 1L;
                }
                return sum / total;
            },
            () -> {
                double sum = 0.0d;
                double total = 0.0d;
                for (final double val : src) {
                    sum += val;
                    total += 1.0d;
                }
                if (total == 0.0d) {
                    total = 1.0d;
                }
                return sum / total;
            },
            () -> {
                float sum = 0.0f;
                float total = 0.0f;
                for (final double val : src) {
                    sum += (float) val;
                    total += 1.0f;
                }
                if (total == 0.0f) {
                    total = 1.0f;
                }
                return sum / total;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Float... src) {
        this(
            () -> {
                int sum = 0;
                int total = 0;
                for (final float val : src) {
                    sum += (int) val;
                    ++total;
                }
                if (total == 0) {
                    total = 1;
                }
                return sum / total;
            },
            () -> {
                long sum = 0L;
                long total = 0L;
                for (final float val : src) {
                    sum += (long) val;
                    ++total;
                }
                if (total == 0L) {
                    total = 1L;
                }
                return sum / total;
            },
            () -> {
                double sum = 0.0d;
                double total = 0.0d;
                for (final float val : src) {
                    sum += (double) val;
                    total += 1.0d;
                }
                if (total == 0.0d) {
                    total = 1.0d;
                }
                return sum / total;
            },
            () -> {
                float sum = 0.0f;
                float total = 0.0f;
                for (final float val : src) {
                    sum += val;
                    total += 1.0f;
                }
                if (total == 0.0f) {
                    total = 1.0f;
                }
                return sum / total;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    @SafeVarargs
    public AvgOf(final Scalar<Number>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public AvgOf(final Iterable<Scalar<Number>> src) {
        this(
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                int sum = 0;
                int total = 0;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.intValue();
                    ++total;
                }
                if (total == 0) {
                    total = 1;
                }
                return sum / total;
            },
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                long sum = 0L;
                long total = 0L;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.longValue();
                    ++total;
                }
                if (total == 0L) {
                    total = 1L;
                }
                return sum / total;
            },
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                double sum = 0.0d;
                double total = 0.0d;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.doubleValue();
                    total += 1.0d;
                }
                if (total == 0.0d) {
                    total = 1.0d;
                }
                return sum / total;
            },
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                float sum = 0.0f;
                float total = 0.0f;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.floatValue();
                    total += 1.0f;
                }
                if (total == 0.0f) {
                    total = 1.0f;
                }
                return sum / total;
            }
        );
    }

    /**
     * Ctor.
     * @param isr Integer
     * @param lsr Long
     * @param dsr Double
     * @param fsr Float
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    private AvgOf(final Scalar<Integer> isr, final Scalar<Long> lsr,
        final Scalar<Double> dsr, final Scalar<Float> fsr) {
        super();
        this.lsum = new StickyScalar<>(lsr);
        this.isum = new StickyScalar<>(isr);
        this.dsum = new StickyScalar<>(dsr);
        this.fsum = new StickyScalar<>(fsr);
    }

    @Override
    public int intValue() {
        return new UncheckedScalar<>(this.isum).value();
    }

    @Override
    public long longValue() {
        return new UncheckedScalar<>(this.lsum).value();
    }

    @Override
    public float floatValue() {
        return new UncheckedScalar<>(this.fsum).value();
    }

    @Override
    public double doubleValue() {
        return new UncheckedScalar<>(this.dsum).value();
    }

}
