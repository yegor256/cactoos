/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link AvgOf}.
 *
 * @since 0.24
 */
final class AvgOfTest {

    @Test
    void withEmptyCollection() {
        MatcherAssert.assertThat(
            "Average of elements in empty collection must be zero",
            new AvgOf(Collections.emptyList()).longValue(),
            new IsEqual<>(0L)
        );
    }

    @Test
    void withIntCollectionIntValue() {
        MatcherAssert.assertThat(
            "Average of values in int collection must be int value",
            new AvgOf(
                1, 2, 3, 4
            ).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    void withIntCollectionIntValueMaxValues() {
        MatcherAssert.assertThat(
            "Average of values in MAX int collection must be MAX int value",
            new AvgOf(
                Integer.MAX_VALUE, Integer.MAX_VALUE
            ).intValue(),
            new IsEqual<>(Integer.MAX_VALUE)
        );
    }

    @Test
    void withIntCollectionLongValue() {
        MatcherAssert.assertThat(
            "Average of values in int collection must be long value",
            new AvgOf(
                1, 2, 3, 4
            ).longValue(),
            new IsEqual<>(2L)
        );
    }

    @Test
    void withIntCollectionDoubleValue() {
        MatcherAssert.assertThat(
            "Average of values in int collection must be double value",
            new AvgOf(
                1, 2, 3, 4
            ).doubleValue(),
            new IsEqual<>(2.5d)
        );
    }

    @Test
    void withIntCollectionFloatValue() {
        MatcherAssert.assertThat(
            "Average of values in int collection must be float value",
            new AvgOf(
                1, 2, 3, 4
            ).floatValue(),
            new IsEqual<>(2.5f)
        );
    }

    @Test
    void withLongCollectionIntValue() {
        MatcherAssert.assertThat(
            "Average of values in long collection must be int value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    void withLongCollectionLongValue() {
        MatcherAssert.assertThat(
            "Average of values in long collection must be long value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).longValue(),
            new IsEqual<>(2L)
        );
    }

    @Test
    void withLongCollectionMaxValue() {
        MatcherAssert.assertThat(
            "Average of values in MAX long collection must be MAX long value",
            new AvgOf(
                Long.MAX_VALUE, Long.MAX_VALUE
            ).longValue(),
            new IsEqual<>(Long.MAX_VALUE)
        );
    }

    @Test
    void withLongCollectionDoubleValue() {
        MatcherAssert.assertThat(
            "Average of values in long collection must be double value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).doubleValue(),
            new IsEqual<>(2.5d)
        );
    }

    @Test
    void withLongCollectionFloatValue() {
        MatcherAssert.assertThat(
            "Average of values in long collection must be float value",
            new AvgOf(
                1L, 2L, 3L, 4L
            ).floatValue(),
            new IsEqual<>(2.5f)
        );
    }

    @Test
    void withDoubleCollectionIntValue() {
        MatcherAssert.assertThat(
            "Average of values in double collection must be int value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    void withDoubleCollectionLongValue() {
        MatcherAssert.assertThat(
            "Average of values in double collection must be long value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).longValue(),
            new IsEqual<>(2L)
        );
    }

    @Test
    void withDoubleCollectionDoubleValue() {
        MatcherAssert.assertThat(
            "Average of values in double collection must be double value",
            new AvgOf(
                1.0d, 2.0d, 3.0d, 4.0d
            ).doubleValue(),
            new IsEqual<>(2.5d)
        );
    }

    @Test
    void withDoubleCollectionMaxValue() {
        MatcherAssert.assertThat(
            "Average of values in MAX double collection must be MAX double value",
            new AvgOf(
                Double.MAX_VALUE, Double.MAX_VALUE
            ).doubleValue(),
            new IsEqual<>(Double.MAX_VALUE)
        );
    }

    @Test
    void withDoubleCollectionMinValue() {
        MatcherAssert.assertThat(
            "Average of values in MIN double collection must be MIN double value",
            new AvgOf(
                Double.MIN_VALUE, Double.MIN_VALUE
            ).doubleValue(),
            new IsEqual<>(Double.MIN_VALUE)
        );
    }

    @Test
    void withDoubleCollectionPositiveInfinity() {
        MatcherAssert.assertThat(
            "can't calculate avg from infinity",
            () -> new AvgOf(
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
            ).doubleValue(),
            new Throws<>(NumberFormatException.class)
        );
    }

    @Test
    void withDoubleCollectionNegativeInfinity() {
        MatcherAssert.assertThat(
            "can't calculate avg from infinity",
            () -> new AvgOf(
                Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY
            ).doubleValue(),
            new Throws<>(NumberFormatException.class)
        );
    }

    @Test
    void withDoubleCollectionNaN() {
        MatcherAssert.assertThat(
            "can't calculate avg from NaN",
            () -> new AvgOf(
                Double.NaN, Double.NaN
            ).doubleValue(),
            new Throws<>(NumberFormatException.class)
        );
    }

    @Test
    void withDoubleCollectionNegativeNumbersDoubleValue() {
        MatcherAssert.assertThat(
            "Average of values in negative double collection must be negative double value",
            new AvgOf(
                -1.0d, -2.0d, -3.0d, -4.0d
            ).doubleValue(),
            new IsEqual<>(-2.5d)
        );
    }

    @Test
    void withDecimalCollectionPrecisionProblem() {
        MatcherAssert.assertThat(
            "Average of decimal values must have precision problem",
            new AvgOf(
                100.0, 100.666, 100.0
            ).floatValue(),
            new IsEqual<>(100.222f)
        );
    }

    @Test
    void withDecimalCollectionPrecisionProblemExtraDecimalRange() {
        MatcherAssert.assertThat(
            "Average of decimal values with extra decimal range must have precision problem",
            new AvgOf(
                100.266, 100.267
            ).floatValue(),
            new IsEqual<>(100.2665f)
        );
    }

    @Test
    void withFloatCollectionIntValue() {
        MatcherAssert.assertThat(
            "Average of float values in float collection must be int value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    void withFloatCollectionLongValue() {
        MatcherAssert.assertThat(
            "Average of values in float collection must be long value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).longValue(),
            new IsEqual<>(2L)
        );
    }

    @Test
    void withFloatCollectionDoubleValue() {
        MatcherAssert.assertThat(
            "Average of values in float collection must be double value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).doubleValue(),
            new IsEqual<>(2.5d)
        );
    }

    @Test
    void withFloatCollectionFloatValue() {
        MatcherAssert.assertThat(
            "Average of values in float collection must be float value",
            new AvgOf(
                1.0f, 2.0f, 3.0f, 4.0f
            ).floatValue(),
            new IsEqual<>(2.5f)
        );
    }

    @Test
    void withFloatCollectionMaxValue() {
        MatcherAssert.assertThat(
            "Average of values in MAX float collection must be MAX float value",
            new AvgOf(
                Float.MAX_VALUE, Float.MAX_VALUE
            ).floatValue(),
            new IsEqual<>(Float.MAX_VALUE)
        );
    }

    @Test
    void withFloatCollectionMinValue() {
        MatcherAssert.assertThat(
            "Average of values in MIN float collection must be MIN float value",
            new AvgOf(
                Float.MIN_VALUE, Float.MIN_VALUE
            ).floatValue(),
            new IsEqual<>(Float.MIN_VALUE)
        );
    }

    @Test
    void canBeCalledTwice() {
        MatcherAssert.assertThat(
            "Average of values can be called twice",
            new AvgOf(
                1, 2, 3, 4
            ),
            new AllOf<Number>(new IsNumber(2.5), new IsNumber(2.5))
        );
    }
}
