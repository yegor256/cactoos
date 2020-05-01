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
package org.cactoos.scalar;

import java.util.Collections;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link AvgOf}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class AvgOfTest {

    @Test
    public void withEmptyCollection() {
        new Assertion<>(
            "Average of elements in empty collection must be zero",
            new AvgOf(Collections.emptyList()).longValue(),
            Matchers.equalTo(0L)
        ).affirm();
    }

    @Test
    public void withIntCollectionIntValue() {
        new Assertion<>(
            "Average of int values in int collection must be int value",
            new AvgOf(
                1, 2, 3, 4
            ).intValue(),
            Matchers.equalTo(2)
        ).affirm();
    }

    @Test
    public void withIntCollectionIntValueMaxValues() {
        new Assertion<>(
            "Average of MAX int values in MAX int collection must be MAX int value",
            new AvgOf(
                Integer.MAX_VALUE, Integer.MAX_VALUE
            ).intValue(),
            Matchers.equalTo(Integer.MAX_VALUE)
        ).affirm();
    }

    @Test
    public void withIntCollectionLongValue() {
        new Assertion<>(
            "Average of int values in int collection must be long value",
            new AvgOf(
                1, 2, 3, 4
            ).longValue(),
            Matchers.equalTo(2L)
        ).affirm();
    }

    @Test
    public void withIntCollectionDoubleValue() {
        new Assertion<>(
            "Average of int values in int collection must be double value",
            new AvgOf(
                1, 2, 3, 4
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        ).affirm();
    }

    @Test
    public void withIntCollectionFloatValue() {
        new Assertion<>(
            "Average of int values in int collection must be float value",
            new AvgOf(
                1, 2, 3, 4
            ).floatValue(),
            Matchers.equalTo(2.5f)
        ).affirm();
    }

    @Test
    public void withLongCollectionIntValue() {
        new Assertion<>(
            "Average of long values in long collection must be int value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).intValue(),
            Matchers.equalTo(2)
        ).affirm();
    }

    @Test
    public void withLongCollectionLongValue() {
        new Assertion<>(
            "Average of long values in long collection must be long value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).longValue(),
            Matchers.equalTo(2L)
        ).affirm();
    }

    @Test
    public void withLongCollectionMaxValue() {
        new Assertion<>(
            "Average of MAX long values in MAX long collection must be MAX long value",
            new AvgOf(
                Long.MAX_VALUE, Long.MAX_VALUE
            ).longValue(),
            Matchers.equalTo(Long.MAX_VALUE)
        ).affirm();
    }

    @Test
    public void withLongCollectionDoubleValue() {
        new Assertion<>(
            "Average of long values in long collection must be double value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        ).affirm();
    }

    @Test
    public void withLongCollectionFloatValue() {
        new Assertion<>(
            "Average of long values in long collection must be float value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).floatValue(),
            Matchers.equalTo(2.5f)
        ).affirm();
    }

    @Test
    public void withDoubleCollectionIntValue() {
        new Assertion<>(
            "Average of double values in double collection must be int value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).intValue(),
            Matchers.equalTo(2)
        ).affirm();
    }

    @Test
    public void withDoubleCollectionLongValue() {
        new Assertion<>(
            "Average of double values in double collection must be long value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).longValue(),
            Matchers.equalTo(2L)
        ).affirm();
    }

    @Test
    public void withDoubleCollectionDoubleValue() {
        new Assertion<>(
            "Average of double values in double collection must be double value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        ).affirm();
    }

    @Test
    public void withDoubleCollectionMaxValue() {
        new Assertion<>(
            "Average of MAX double values in MAX double collection must be MAX double value",
            new AvgOf(
                Double.MAX_VALUE, Double.MAX_VALUE
            ).doubleValue(),
            Matchers.equalTo(Double.MAX_VALUE)
        ).affirm();
    }

    @Test
    public void withDoubleCollectionMinValue() {
        new Assertion<>(
            "Average of MIN double values in MIN double collection must be MIN double value",
            new AvgOf(
                Double.MIN_VALUE, Double.MIN_VALUE
            ).doubleValue(),
            Matchers.equalTo(Double.MIN_VALUE)
        ).affirm();
    }

    @Test(expected = NumberFormatException.class)
    public void withDoubleCollectionPositiveInfinity() {
        new AvgOf(
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
        ).doubleValue();
    }

    @Test(expected = NumberFormatException.class)
    public void withDoubleCollectionNegativeInfinity() {
        new AvgOf(
            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY
        ).doubleValue();
    }

    @Test(expected = NumberFormatException.class)
    public void withDoubleCollectionNaN() {
        new AvgOf(
            Double.NaN, Double.NaN
        ).doubleValue();
    }

    @Test
    public void withDoubleCollectionNegativeNumbersDoubleValue() {
        new Assertion<>(
            "Average of negative double values in negative double collection must be negative double value",
            new AvgOf(
                -1.0d, -2.0d, -3.0d, -4.0d
            ).doubleValue(),
            Matchers.equalTo(-2.5d)
        ).affirm();
    }

    @Test
    public void withDecimalCollectionPrecisionProblem() {
        new Assertion<>(
            "Average of decimal values must have precision problem",
            new AvgOf(
                100.0, 100.666, 100.0
            ).floatValue(),
            Matchers.equalTo(100.222f)
        ).affirm();
    }

    @Test
    public void withDecimalCollectionPrecisionProblemExtraDecimalRange() {
        new Assertion<>(
            "Average of decimal values with extra decimal range must have precision problem",
            new AvgOf(
                100.266, 100.267
            ).floatValue(),
            Matchers.equalTo(100.2665f)
        ).affirm();
    }

    @Test
    public void withFloatCollectionIntValue() {
        new Assertion<>(
            "Average of float values in float collection must be int value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).intValue(),
            Matchers.equalTo(2)
        ).affirm();
    }

    @Test
    public void withFloatCollectionLongValue() {
        new Assertion<>(
            "Average of float values in float collection must be long value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).longValue(),
            Matchers.equalTo(2L)
        ).affirm();
    }

    @Test
    public void withFloatCollectionDoubleValue() {
        new Assertion<>(
            "Average of float values in float collection must be double value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        ).affirm();
    }

    @Test
    public void withFloatCollectionFloatValue() {
        new Assertion<>(
            "Average of float values in float collection must be float value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).floatValue(),
            Matchers.equalTo(2.5f)
        ).affirm();
    }

    @Test
    public void withFloatCollectionMaxValue() {
        new Assertion<>(
            "Average of MAX float values in MAX float collection must be MAX float value",
            new AvgOf(
                Float.MAX_VALUE, Float.MAX_VALUE
            ).floatValue(),
            Matchers.equalTo(Float.MAX_VALUE)
        ).affirm();
    }

    @Test
    public void withFloatCollectionMinValue() {
        new Assertion<>(
            "Average of MIN float values in MIN float collection must be MIN float value",
            new AvgOf(
                Float.MIN_VALUE, Float.MIN_VALUE
            ).floatValue(),
            Matchers.equalTo(Float.MIN_VALUE)
        ).affirm();
    }

    @Test
    public void withIntScalarsIntValue() {
        new Assertion<>(
            "Average of int scalars must be int value",
            new AvgOf(
                () -> 1, () -> 2, () -> 10
            ).intValue(),
            Matchers.equalTo(4)
        ).affirm();
    }

    @Test
    public void withLongScalarsIntValue() {
        new Assertion<>(
            "Average of long scalars must be int value",
            new AvgOf(
                () -> 1L, () -> 2L, () -> 10L
            ).intValue(),
            Matchers.equalTo(4)
        ).affirm();
    }

    @Test
    public void withFloatScalarsIntValue() {
        new Assertion<>(
            "Average of float scalars must be int value",
            new AvgOf(
                () -> 1.0f, () -> 2.0f, () -> 10.0f
            ).longValue(),
            Matchers.equalTo(4L)
        ).affirm();
    }

    @Test
    public void withDoubleScalarsIntValue() {
        new Assertion<>(
            "Average of double scalars must be int value",
            new AvgOf(
                () -> 1.0d, () -> 2.0d, () -> 10.0d
            ).intValue(),
            Matchers.equalTo(4)
        ).affirm();
    }

    @Test
    public void withIterableOfScalars() {
        new Assertion<>(
            "Average of iterable scalars must be long value",
            new AvgOf(
                new IterableOf<Scalar<Number>>(() -> 1L, () -> 2L, () -> 10L)
            ).longValue(),
            Matchers.equalTo(4L)
        ).affirm();
    }

    @Test
    public void withCompositionOfScalars() {
        new Assertion<>(
            "Average of composition of scalars must be int value",
            new AvgOf(
                () -> new MinOf(1.0d, 2.0d),
                () -> new MaxOf(2.0d, 4.0d),
                () -> new SumOf(1.0d, 2.0d, 2.0d),
                new Ternary<>(true, 5.0d, 1.0d)
            ).intValue(),
            Matchers.equalTo(3)
        ).affirm();
    }
}
