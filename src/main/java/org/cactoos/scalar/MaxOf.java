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

/**
 * Find the greater among items.
 *
 * <p>Here is how you can use it to summarize numbers:</p>
 *
 * <pre>
 * int sum = new MaxOf(1, 2, 3, 4).intValue();
 * long sum = new MaxOf(1L, 2L, 3L).longValue();
 * int sum = new MaxOf(numbers.toArray(new Integer[numbers.size()])).intValue();
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @see UncheckedScalar
 * @see IoCheckedScalar
 * @since 0.24
 */
@SuppressWarnings(
    {
        "PMD.CallSuperInConstructor",
        "PMD.OnlyOneConstructorShouldDoInitialization",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
    }
)
public final class MaxOf extends Number {

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
    public MaxOf(final Integer... src) {
        this(
            () -> {
                int max = Integer.MIN_VALUE;
                for (final int val : src) {
                    if (val > max) {
                        max = val;
                    }
                }
                return max;
            },
            () -> {
                long max = Long.MIN_VALUE;
                for (final int val : src) {
                    if ((long) val > max) {
                        max = (long) val;
                    }
                }
                return max;
            },
            () -> {
                double max = Double.MIN_VALUE;
                for (final int val : src) {
                    if ((double) val > max) {
                        max = (double) val;
                    }
                }
                return max;
            },
            () -> {
                float max = Float.MIN_VALUE;
                for (final int val : src) {
                    if ((float) val > max) {
                        max = (float) val;
                    }
                }
                return max;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MaxOf(final Long... src) {
        this(
            () -> {
                int max = Integer.MIN_VALUE;
                for (final long val : src) {
                    if ((int) val > max) {
                        max = (int) val;
                    }
                }
                return max;
            },
            () -> {
                long max = Long.MIN_VALUE;
                for (final long val : src) {
                    if (val > max) {
                        max = val;
                    }
                }
                return max;
            },
            () -> {
                double max = Double.MIN_VALUE;
                for (final long val : src) {
                    if ((double) val > max) {
                        max = (double) val;
                    }
                }
                return max;
            },
            () -> {
                float max = Float.MIN_VALUE;
                for (final long val : src) {
                    if ((float) val > max) {
                        max = (float) val;
                    }
                }
                return max;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MaxOf(final Double... src) {
        this(
            () -> {
                int max = Integer.MIN_VALUE;
                for (final double val : src) {
                    if ((int) val > max) {
                        max = (int) val;
                    }
                }
                return max;
            },
            () -> {
                long max = Long.MIN_VALUE;
                for (final double val : src) {
                    if ((long) val > max) {
                        max = (long) val;
                    }
                }
                return max;
            },
            () -> {
                double max = Double.MIN_VALUE;
                for (final double val : src) {
                    if (val > max) {
                        max = val;
                    }
                }
                return max;
            },
            () -> {
                float max = Float.MIN_VALUE;
                for (final double val : src) {
                    if ((float) val > max) {
                        max = (float) val;
                    }
                }
                return max;
            }
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MaxOf(final Float... src) {
        this(
            () -> {
                int max = Integer.MIN_VALUE;
                for (final float val : src) {
                    if ((int) val > max) {
                        max = (int) val;
                    }
                }
                return max;
            },
            () -> {
                long max = Long.MIN_VALUE;
                for (final float val : src) {
                    if ((long) val > max) {
                        max = (long) val;
                    }
                }
                return max;
            },
            () -> {
                double max = Double.MIN_VALUE;
                for (final float val : src) {
                    if ((double) val > max) {
                        max = (double) val;
                    }
                }
                return max;
            },
            () -> {
                float max = Float.MIN_VALUE;
                for (final float val : src) {
                    if (val > max) {
                        max = val;
                    }
                }
                return max;
            }
        );
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public MaxOf(final Iterable<Number> src) {
        this(
            () -> {
                final Iterator<Number> numbers = src.iterator();
                int max = Integer.MIN_VALUE;
                while (numbers.hasNext()) {
                    final int next = numbers.next().intValue();
                    if (next > max) {
                        max = next;
                    }
                }
                return max;
            },
            () -> {
                final Iterator<Number> numbers = src.iterator();
                long max = Long.MIN_VALUE;
                while (numbers.hasNext()) {
                    final long next = numbers.next().longValue();
                    if (next > max) {
                        max = next;
                    }
                }
                return max;
            },
            () -> {
                final Iterator<Number> numbers = src.iterator();
                double max = Double.MIN_VALUE;
                while (numbers.hasNext()) {
                    final double next = numbers.next().doubleValue();
                    if (next > max) {
                        max = next;
                    }
                }
                return max;
            },
            () -> {
                final Iterator<Number> numbers = src.iterator();
                float max = Float.MIN_VALUE;
                while (numbers.hasNext()) {
                    final float next = numbers.next().floatValue();
                    if (next > max) {
                        max = next;
                    }
                }
                return max;
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
    private MaxOf(final Scalar<Integer> isr, final Scalar<Long> lsr,
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
