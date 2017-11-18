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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Int total of numbers.
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
public final class SumOf extends Number implements Scalar<Number> {

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
     * @since 0.22
     */
    public SumOf(final int... src) {
        this(new Mapped<>(
            input -> () -> input,
            IntStream.of(src).boxed().collect(Collectors.toList())
        ));
    }

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final long... src) {
        this(new Mapped<>(
            input -> () -> input,
            LongStream.of(src).boxed().collect(Collectors.toList())
        ));
    }

    /**
     * Ctor.
     * @param src Numbers
     * @since 0.22
     */
    public SumOf(final double... src) {
        this(new Mapped<>(
            input -> () -> input,
            DoubleStream.of(src).boxed().collect(Collectors.toList())
        ));
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    @SafeVarargs
    public SumOf(final Scalar<Number>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public SumOf(final Iterable<Scalar<Number>> src) {
        super();
        this.lsum = new StickyScalar<>(
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                long sum = 0L;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.longValue();
                }
                return sum;
            }
        );
        this.isum = new StickyScalar<>(
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                int sum = 0;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.intValue();
                }
                return sum;
            }
        );
        this.fsum = new StickyScalar<>(
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                float sum = 0.0f;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.floatValue();
                }
                return sum;
            }
        );
        this.dsum = new StickyScalar<>(
            () -> {
                final Iterator<Scalar<Number>> numbers = src.iterator();
                double sum = 0.0d;
                while (numbers.hasNext()) {
                    final Number next = numbers.next().value();
                    sum += next.doubleValue();
                }
                return sum;
            }
        );
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

    @Override
    public Number value() {
        return this;
    }

}
