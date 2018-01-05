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
 * Find the smaller among items.
 *
 * <p>Here is how you can use it to summarize numbers:</p>
 *
 * <pre>
 * int sum = new MinOf(1, 2, 3, 4).intValue();
 * long sum = new MinOf(1L, 2L, 3L).longValue();
 * int sum = new MinOf(numbers.toArray(new Integer[numbers.size()])).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.24
 */
@SuppressWarnings(
    {
        "PMD.CallSuperInConstructor",
        "PMD.OnlyOneConstructorShouldDoInitialization",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
    }
)
public final class MinOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 7081070952159683108L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public MinOf(final Integer... src) {
        super(() -> {
            long min = Long.MAX_VALUE;
            for (final int val : src) {
                if ((long) val < min) {
                    min = (long) val;
                }
            }
            return min;
        }, () -> {
            int min = Integer.MAX_VALUE;
            for (final int val : src) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        }, () -> {
            float min = Float.MAX_VALUE;
            for (final int val : src) {
                if ((float) val < min) {
                    min = (float) val;
                }
            }
            return min;
        }, () -> {
            double min = Double.MAX_VALUE;
            for (final int val : src) {
                if ((double) val < min) {
                    min = (double) val;
                }
            }
            return min;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MinOf(final Long... src) {
        super(() -> {
            long min = Long.MAX_VALUE;
            for (final long val : src) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        }, () -> {
            int min = Integer.MAX_VALUE;
            for (final long val : src) {
                if ((int) val < min) {
                    min = (int) val;
                }
            }
            return min;
        }, () -> {
            float min = Float.MAX_VALUE;
            for (final long val : src) {
                if ((float) val < min) {
                    min = (float) val;
                }
            }
            return min;
        }, () -> {
            double min = Double.MAX_VALUE;
            for (final long val : src) {
                if ((double) val < min) {
                    min = (double) val;
                }
            }
            return min;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MinOf(final Double... src) {
        super(() -> {
            long min = Long.MAX_VALUE;
            for (final double val : src) {
                if ((long) val < min) {
                    min = (long) val;
                }
            }
            return min;
        }, () -> {
            int min = Integer.MAX_VALUE;
            for (final double val : src) {
                if ((int) val < min) {
                    min = (int) val;
                }
            }
            return min;
        }, () -> {
            float min = Float.MAX_VALUE;
            for (final double val : src) {
                if ((float) val < min) {
                    min = (float) val;
                }
            }
            return min;
        }, () -> {
            double min = Double.MAX_VALUE;
            for (final double val : src) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        });
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public MinOf(final Float... src) {
        super(() -> {
            long min = Long.MAX_VALUE;
            for (final float val : src) {
                if ((long) val < min) {
                    min = (long) val;
                }
            }
            return min;
        }, () -> {
            int min = Integer.MAX_VALUE;
            for (final float val : src) {
                if ((int) val < min) {
                    min = (int) val;
                }
            }
            return min;
        }, () -> {
            float min = Float.MAX_VALUE;
            for (final float val : src) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        }, () -> {
            double min = Double.MAX_VALUE;
            for (final float val : src) {
                if ((double) val < min) {
                    min = (double) val;
                }
            }
            return min;
        });
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public MinOf(final Iterable<Number> src) {
        super(() -> {
            final Iterator<Number> numbers = src.iterator();
            long min = Long.MAX_VALUE;
            while (numbers.hasNext()) {
                final long next = numbers.next().longValue();
                if (next < min) {
                    min = next;
                }
            }
            return min;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            int min = Integer.MAX_VALUE;
            while (numbers.hasNext()) {
                final int next = numbers.next().intValue();
                if (next < min) {
                    min = next;
                }
            }
            return min;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            float min = Float.MAX_VALUE;
            while (numbers.hasNext()) {
                final float next = numbers.next().floatValue();
                if (next < min) {
                    min = next;
                }
            }
            return min;
        }, () -> {
            final Iterator<Number> numbers = src.iterator();
            double min = Double.MAX_VALUE;
            while (numbers.hasNext()) {
                final double next = numbers.next().doubleValue();
                if (next < min) {
                    min = next;
                }
            }
            return min;
        });
    }

}
