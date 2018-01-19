/**
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

import java.util.Iterator;
import org.cactoos.Scalar;

/**
 * Int total of numbers.
 *
 * <p>Here is how you can use it to summarize numbers:</p>
 *
 * <pre>
 * int sum = new SumOf(1, 2, 3, 4).intValue();
 * long sum = new SumOf(1L, 2L, 3L).longValue();
 * int sum = new SumOf(numbers.toArray(new Integer[numbers.size()])).intValue();
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
 * @since 0.9
 */
@SuppressWarnings(
    {
        "PMD.CallSuperInConstructor",
        "PMD.OnlyOneConstructorShouldDoInitialization",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
    }
)
public final class SumOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 7775359972001208403L;

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Integer... src) {
        super(() -> {
            long sum = 0L;
            for (final int val : src) {
                sum += (long) val;
            }
            return sum;
        }, () -> {
            int sum = 0;
            for (final int val : src) {
                sum += val;
            }
            return sum;
        }, () -> {
            float sum = 0.0f;
            for (final int val : src) {
                sum += (float) val;
            }
            return sum;
        }, () -> {
            double sum = 0.0d;
            for (final int val : src) {
                sum += (double) val;
            }
            return sum;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Long... src) {
        super(() -> {
            long sum = 0L;
            for (final long val : src) {
                sum += val;
            }
            return sum;
        }, () -> {
            int sum = 0;
            for (final long val : src) {
                sum += (int) val;
            }
            return sum;
        }, () -> {
            float sum = 0.0f;
            for (final long val : src) {
                sum += (float) val;
            }
            return sum;
        }, () -> {
            double sum = 0.0d;
            for (final long val : src) {
                sum += (double) val;
            }
            return sum;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Double... src) {
        super(() -> {
            long sum = 0L;
            for (final double val : src) {
                sum += (long) val;
            }
            return sum;
        }, () -> {
            int sum = 0;
            for (final double val : src) {
                sum += (int) val;
            }
            return sum;
        }, () -> {
            float sum = 0.0f;
            for (final double val : src) {
                sum += (float) val;
            }
            return sum;
        }, () -> {
            double sum = 0.0d;
            for (final double val : src) {
                sum += val;
            }
            return sum;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final Float... src) {
        super(() -> {
            long sum = 0L;
            for (final float val : src) {
                sum += (long) val;
            }
            return sum;
        }, () -> {
            int sum = 0;
            for (final float val : src) {
                sum += (int) val;
            }
            return sum;
        }, () -> {
            float sum = 0.0f;
            for (final float val : src) {
                sum += val;
            }
            return sum;
        }, () -> {
            double sum = 0.0d;
            for (final float val : src) {
                sum += (double) val;
            }
            return sum;
        });
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public SumOf(final Iterable<Number> src) {
        super(() -> {
            final Iterator<Number> numbers = src.iterator();
            long sum = 0L;
            while (numbers.hasNext()) {
                sum += numbers.next().longValue();
            }
            return sum;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            int sum = 0;
            while (numbers.hasNext()) {
                sum += numbers.next().intValue();
            }
            return sum;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            float sum = 0.0f;
            while (numbers.hasNext()) {
                sum += numbers.next().floatValue();
            }
            return sum;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            double sum = 0.0d;
            while (numbers.hasNext()) {
                sum += numbers.next().doubleValue();
            }
            return sum;
        });
    }

}
