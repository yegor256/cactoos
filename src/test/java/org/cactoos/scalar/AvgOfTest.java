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

import java.util.Collections;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

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
        MatcherAssert.assertThat(
            new AvgOf(Collections.emptyList()).longValue(),
            Matchers.equalTo(0L)
        );
    }

    @Test
    public void withIntCollectionIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1, 2, 3, 4
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void withIntCollectionIntValueMaxValues() {
        MatcherAssert.assertThat(
            new AvgOf(
                Integer.MAX_VALUE, Integer.MAX_VALUE
            ).intValue(),
            Matchers.equalTo(Integer.MAX_VALUE)
        );
    }

    @Test
    public void withIntCollectionLongValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1, 2, 3, 4
            ).longValue(),
            Matchers.equalTo(2L)
        );
    }

    @Test
    public void withIntCollectionDoubleValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1, 2, 3, 4
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        );
    }

    @Test
    public void withIntCollectionFloatValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1, 2, 3, 4
            ).floatValue(),
            Matchers.equalTo(2.5f)
        );
    }

    @Test
    public void withLongCollectionIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1L, 2L, 3L, 4L
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void withLongCollectionLongValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1L, 2L, 3L, 4L
            ).longValue(),
            Matchers.equalTo(2L)
        );
    }

    @Test
    public void withLongCollectionMaxValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                Long.MAX_VALUE, Long.MAX_VALUE
            ).longValue(),
            Matchers.equalTo(Long.MAX_VALUE)
        );
    }

    @Test
    public void withLongCollectionDoubleValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1L, 2L, 3L, 4L
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        );
    }

    @Test
    public void withLongCollectionFloatValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1L, 2L, 3L, 4L
            ).floatValue(),
            Matchers.equalTo(2.5f)
        );
    }

    @Test
    public void withDoubleCollectionIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void withDoubleCollectionLongValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).longValue(),
            Matchers.equalTo(2L)
        );
    }

    @Test
    public void withDoubleCollectionDoubleValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        );
    }

    @Test
    public void withDoubleCollectionMaxValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                Double.MAX_VALUE, Double.MAX_VALUE
            ).doubleValue(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
    }

    @Test
    public void withDoubleCollectionMinValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                Double.MIN_VALUE, Double.MIN_VALUE
            ).doubleValue(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
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
        MatcherAssert.assertThat(
            new AvgOf(
                -1.0d, -2.0d, -3.0d, -4.0d
            ).doubleValue(),
            Matchers.equalTo(-2.5d)
        );
    }

    @Test
    public void withDecimalCollectionPrecisionProblem() {
        MatcherAssert.assertThat(
            new AvgOf(
                100.0, 100.666, 100.0
            ).floatValue(),
            Matchers.equalTo(100.222f)
        );
    }

    @Test
    public void withDecimalCollectionPrecisionProblemExtraDecimalRange() {
        MatcherAssert.assertThat(
            new AvgOf(
                100.266, 100.267
            ).floatValue(),
            Matchers.equalTo(100.2665f)
        );
    }

    @Test
    public void withFloatCollectionIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void withFloatCollectionLongValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).longValue(),
            Matchers.equalTo(2L)
        );
    }

    @Test
    public void withFloatCollectionDoubleValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).doubleValue(),
            Matchers.equalTo(2.5d)
        );
    }

    @Test
    public void withFloatCollectionFloatValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).floatValue(),
            Matchers.equalTo(2.5f)
        );
    }

    @Test
    public void withFloatCollectionMaxValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                Float.MAX_VALUE, Float.MAX_VALUE
            ).floatValue(),
            Matchers.equalTo(Float.MAX_VALUE)
        );
    }

    @Test
    public void withFloatCollectionMinValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                Float.MIN_VALUE, Float.MIN_VALUE
            ).floatValue(),
            Matchers.equalTo(Float.MIN_VALUE)
        );
    }

    @Test
    public void withIntScalarsIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                () -> 1, () -> 2, () -> 10
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void withLongScalarsIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                () -> 1L, () -> 2L, () -> 10L
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void withFloatScalarsIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                () -> 1.0f, () -> 2.0f, () -> 10.0f
            ).longValue(),
            Matchers.equalTo(4L)
        );
    }

    @Test
    public void withDoubleScalarsIntValue() {
        MatcherAssert.assertThat(
            new AvgOf(
                () -> 1.0d, () -> 2.0d, () -> 10.0d
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void withIterableOfScalars() {
        MatcherAssert.assertThat(
            new AvgOf(
                new IterableOf<Scalar<Number>>(() -> 1L, () -> 2L, () -> 10L)
            ).longValue(),
            Matchers.equalTo(4L)
        );
    }

    @Test
    public void withCompositionOfScalars() {
        MatcherAssert.assertThat(
            new AvgOf(
                () -> new MinOf(1.0d, 2.0d),
                () -> new MaxOf(2.0d, 4.0d),
                () -> new SumOf(1.0d, 2.0d, 2.0d),
                new Ternary<>(true, 5.0d, 1.0d)
            ).intValue(),
            Matchers.equalTo(3)
        );
    }
}
