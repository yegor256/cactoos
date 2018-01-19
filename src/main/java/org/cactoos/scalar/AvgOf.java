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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.StreamSupport;
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
public final class AvgOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 3624862553221697558L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Integer... src) {
        super(() -> avgArray(
            number -> BigDecimal.valueOf(number.intValue()), src
            ).longValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.intValue()), src
            ).intValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.intValue()), src
            ).floatValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.intValue()), src
            ).doubleValue()
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Long... src) {
        super(() -> avgArray(
            number -> BigDecimal.valueOf(number.longValue()), src
            ).longValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.longValue()), src
            ).intValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.longValue()), src
            ).floatValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.longValue()), src
            ).doubleValue()
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Double... src) {
        super(() -> avgArray(
            number -> BigDecimal.valueOf(number), src
            ).longValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number), src
            ).intValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number), src
            ).floatValue(),
            () -> avgArray(
                number -> new BigDecimal(number), src
            ).doubleValue()
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Float... src) {
        super(() -> avgArray(
            number -> BigDecimal.valueOf(number.floatValue()), src
            ).longValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.floatValue()), src
            ).intValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.floatValue()), src
            ).floatValue(),
            () -> avgArray(
                number -> BigDecimal.valueOf(number.floatValue()), src
            ).doubleValue()
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
        super(() -> avgIterator(
            number -> BigDecimal.valueOf(number.longValue()), src
            ).longValue(),
            () -> avgIterator(
                number -> BigDecimal.valueOf(number.intValue()), src
            ).intValue(),
            () -> avgIterator(
                number -> BigDecimal.valueOf(number.floatValue()), src
            ).floatValue(),
            () -> avgIterator(
                number -> BigDecimal.valueOf(number.doubleValue()), src
            ).doubleValue()
        );
    }

    /**
     * Calculates average from the provided numbers and uses {@link Converter}
     * to create {@link BigDecimal}.
     * @param converter The {@link Converter} to create {@link BigDecimal}
     * @param src The numbers to calculate average.
     * @return The average.
     */
    private static BigDecimal avgIterator(
        final Converter<Number> converter, final Iterable<Scalar<Number>> src
    ) {
        return avgArray(
            converter,
            StreamSupport.stream(src.spliterator(), false)
                .map(scalar -> new UncheckedScalar<>(scalar).value())
                .toArray(Number[]::new)
        );
    }

    /**
     * Calculates average from the provided numbers and uses {@link Converter}
     * to create {@link BigDecimal}.
     * @param converter The {@link Converter} to create {@link BigDecimal}
     * @param src The numbers to calculate average.
     * @param <T> The type of numbers.
     * @return The average.
     */
    @SafeVarargs
    private static <T extends Number> BigDecimal avgArray(
        final Converter<T> converter, final T... src
    ) {
        BigDecimal sum = BigDecimal.ZERO;
        long count = 0;
        for (final T value : src) {
            sum = sum.add(converter.convert(value));
            ++count;
        }
        if (count == 0) {
            count = 1;
        }
        return sum.divide(
            BigDecimal.valueOf(count), MathContext.DECIMAL128
        );
    }

    /**
     * The converter interface to provide {@link BigDecimal}.
     * @param <T> The type of number.
     */
    @FunctionalInterface
    private interface Converter<T extends Number> {
        /**
         * Converts the number to {@link BigDecimal}.
         * @param number The number to convert.
         * @return A {@link BigDecimal}.
         */
        BigDecimal convert(final T number);
    }
}
