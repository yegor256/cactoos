/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import org.cactoos.number.ComparableNumber;
import org.cactoos.number.SumOf;
import org.cactoos.time.DateOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LowestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class LowestOfTest {

    @Test
    void failsForEmptyIterable() {
        MatcherAssert.assertThat(
            "can't iterate in empty collection",
            () -> new LowestOf<>(() -> Collections.emptyIterator()).value(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void singleAtSingleIterableByScalar() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest among one by scalars",
            new LowestOf<Integer>(() -> 10).value(),
            Matchers.equalTo(10)
        );
    }

    @Test
    void singleAtSingleIterableByValue() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest among one",
            new LowestOf<>(10).value(),
            Matchers.equalTo(10)
        );
    }

    @Test
    void lowestIntegerByScalars() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest integer among many by scalars",
            new LowestOf<Integer>(
                () -> 10,
                () -> 0,
                () -> -1,
                () -> 2
            ).value(),
            Matchers.equalTo(-1)
        );
    }

    @Test
    void lowestIntegerByValues() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest integer among many",
            new LowestOf<>(10, 0, -1, 2).value(),
            Matchers.equalTo(-1)
        );
    }

    @Test
    void lowestPositiveInteger() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest positive integer among many",
            new LowestOf<>(272, 10, 134, 101).value(),
            Matchers.equalTo(10)
        );
    }

    @Test
    void lowestNegativeInteger() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest negative integer among many",
            new LowestOf<>(-272, -10, -134, -101).value(),
            Matchers.equalTo(-272)
        );
    }

    @Test
    void lowestMinInteger() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest min integer among many",
            new LowestOf<>(Integer.MIN_VALUE, Integer.MAX_VALUE).value(),
            Matchers.equalTo(Integer.MIN_VALUE)
        );
    }

    @Test
    void lowestLongByScalars() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest long among many by scalars",
            new LowestOf<Long>(
                () -> 10L,
                () -> 0L,
                () -> -1L,
                () -> 2L
            ).value(),
            Matchers.equalTo(-1L)
        );
    }

    @Test
    void lowestLongByValues() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest long among many",
            new LowestOf<>(10L, 0L, -1L, 2L).value(),
            Matchers.equalTo(-1L)
        );
    }

    @Test
    void lowestPositiveLong() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest positive long among many",
            new LowestOf<>(272L, 10L, 134L, 101L).value(),
            Matchers.equalTo(10L)
        );
    }

    @Test
    void lowestNegativeLong() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest negative long among many",
            new LowestOf<>(-272L, -10L, -134L, -101L).value(),
            Matchers.equalTo(-272L)
        );
    }

    @Test
    void lowestMinLong() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest mix integer long many",
            new LowestOf<>(Long.MIN_VALUE, Long.MAX_VALUE).value(),
            Matchers.equalTo(Long.MIN_VALUE)
        );
    }

    @Test
    void lowestDoubleByScalars() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest double among many by scalars",
            new LowestOf<Double>(
                () -> 10.9,
                () -> 0.8,
                () -> -1.5,
                () -> 10.8
            ).value(),
            Matchers.equalTo(-1.5)
        );
    }

    @Test
    void lowestDoubleByValues() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest double among many",
            new LowestOf<>(10., 0., -1., 2.).value(),
            Matchers.equalTo(-1.)
        );
    }

    @Test
    void lowestPositiveDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest positive double among many",
            new LowestOf<>(272., 10., 134., 101.).value(),
            Matchers.equalTo(10.)
        );
    }

    @Test
    void lowestNegativeDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest negative double among many",
            new LowestOf<>(-272., -10., -134., -101.).value(),
            Matchers.equalTo(-272.)
        );
    }

    @Test
    void lowestMixDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest mix double among many",
            new LowestOf<>(Double.MIN_VALUE, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
    }

    @Test
    void lowestMinDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest min double among many",
            new LowestOf<>(Double.MIN_VALUE, 10.).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
    }

    @Test
    void lowestNaNWithMinDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest NaN (with min) double among many",
            new LowestOf<>(Double.NaN, Double.MIN_VALUE).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
    }

    @Test
    void lowestNaNWithMaxDouble() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest NaN (with max) double among many",
            new LowestOf<>(Double.NaN, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
    }

    @Test
    void lowestPositiveInfinity() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest positive infinity among many",
            new LowestOf<>(Double.POSITIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
    }

    @Test
    void lowestNegativeInfinity() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest negative infinity among many",
            new LowestOf<>(Double.NEGATIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.NEGATIVE_INFINITY)
        );
    }

    @Test
    void lowestStringByScalars() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest string among many by scalars",
            new LowestOf<String>(
                () -> "Banana",
                () -> "Apple",
                () -> "Orange"
            ).value(),
            Matchers.equalTo("Apple")
        );
    }

    @Test
    void lowestStringByValues() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest string among many",
            new LowestOf<>("Banana", "Apple", "Orange").value(),
            Matchers.equalTo("Apple")
        );
    }

    @Test
    void lowestCharByScalars() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest char among many by scalars",
            new LowestOf<Character>(() -> 'B', () -> 'U', () -> 'G').value(),
            Matchers.equalTo('B')
        );
    }

    @Test
    void lowestCharByValues() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest char among many",
            new LowestOf<>('H', 'A', 'N', 'D').value(),
            Matchers.equalTo('A')
        );
    }

    @Test
    void lowestSumAtIterable() {
        MatcherAssert.assertThat(
            "Must find the lowest double sum among many",
            new LowestOf<>(
                new ComparableNumber(new SumOf(1.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d, 3.0d))
            ),
            new HasValue<>(1.0d)
        );
    }

    @Test
    void lowestDateAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest date among many",
            new LowestOf<Date>(
                new DateOf("2007-12-26T14:20:16.000000016Z"),
                new DateOf("2007-12-26T14:20:16.000000017Z"),
                new DateOf("2011-10-01T14:15:16.000000017Z")
            ).value(),
            Matchers.equalTo(
                new DateOf("2007-12-26T14:20:16.000000016Z").value()
            )
        );
    }

    @Test
    void lowestBooleanAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest boolean among many",
            new LowestOf<Boolean>(
                new BoolOf("false"),
                new BoolOf("true")
            ).value(),
            Matchers.equalTo(new BoolOf("false").value())
        );
    }
}
