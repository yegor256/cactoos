/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
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
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link LowestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class LowestOfTest {

    @Test
    void failsForEmptyIterable() {
        new Assertion<>(
            "Can't iterate in empty collection",
            () -> new LowestOf<>(() -> Collections.emptyIterator()).value(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void singleAtSingleIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest among one by scalars",
            new LowestOf<Integer>(() -> 10).value(),
            Matchers.equalTo(10)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest among one",
            new LowestOf<>(10).value(),
            Matchers.equalTo(10)
        );
    }

    @Test
    void lowestIntegerAtIterable() throws Exception {
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
        MatcherAssert.assertThat(
            "Can't find the lowest integer among many",
            new LowestOf<>(10, 0, -1, 2).value(),
            Matchers.equalTo(-1)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest positive integer among many",
            new LowestOf<>(272, 10, 134, 101).value(),
            Matchers.equalTo(10)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest negative integer among many",
            new LowestOf<>(-272, -10, -134, -101).value(),
            Matchers.equalTo(-272)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest min integer among many",
            new LowestOf<>(Integer.MIN_VALUE, Integer.MAX_VALUE).value(),
            Matchers.equalTo(Integer.MIN_VALUE)
        );
    }

    @Test
    void lowestLongAtIterable() throws Exception {
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
        MatcherAssert.assertThat(
            "Can't find the lowest long among many",
            new LowestOf<>(10L, 0L, -1L, 2L).value(),
            Matchers.equalTo(-1L)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest positive long among many",
            new LowestOf<>(272L, 10L, 134L, 101L).value(),
            Matchers.equalTo(10L)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest negative long among many",
            new LowestOf<>(-272L, -10L, -134L, -101L).value(),
            Matchers.equalTo(-272L)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest mix integer long many",
            new LowestOf<>(Long.MIN_VALUE, Long.MAX_VALUE).value(),
            Matchers.equalTo(Long.MIN_VALUE)
        );
    }

    @Test
    void lowestDoubleAtIterable() throws Exception {
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
        MatcherAssert.assertThat(
            "Can't find the lowest double among many",
            new LowestOf<>(10., 0., -1., 2.).value(),
            Matchers.equalTo(-1.)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest positive double among many",
            new LowestOf<>(272., 10., 134., 101.).value(),
            Matchers.equalTo(10.)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest negative double among many",
            new LowestOf<>(-272., -10., -134., -101.).value(),
            Matchers.equalTo(-272.)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest mix double among many",
            new LowestOf<>(Double.MIN_VALUE, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest min double among many",
            new LowestOf<>(Double.MIN_VALUE, 10.).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest NaN (with min) double among many",
            new LowestOf<>(Double.NaN, Double.MIN_VALUE).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest NaN (with max) double among many",
            new LowestOf<>(Double.NaN, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest positive infinity among many",
            new LowestOf<>(Double.POSITIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the lowest negative infinity among many",
            new LowestOf<>(Double.NEGATIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.NEGATIVE_INFINITY)
        );
    }

    @Test
    void lowestStringAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest string among many by scalars",
            new LowestOf<String>(
                () -> "Banana",
                () -> "Apple",
                () -> "Orange"
            ).value(),
            Matchers.equalTo("Apple")
        );
        MatcherAssert.assertThat(
            "Can't find the lowest string among many",
            new LowestOf<>("Banana", "Apple", "Orange").value(),
            Matchers.equalTo("Apple")
        );
    }

    @Test
    void lowestCharAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest char among many by scalars",
            new LowestOf<Character>(() -> 'B', () -> 'U', () -> 'G').value(),
            Matchers.equalTo('B')
        );
        MatcherAssert.assertThat(
            "Can't find the lowest char among many",
            new LowestOf<>('H', 'A', 'N', 'D').value(),
            Matchers.equalTo('A')
        );
    }

    @Test
    void lowestSumAtIterable() {
        new Assertion<>(
            "Must find the lowest double sum among many",
            new LowestOf<>(
                new ComparableNumber(new SumOf(1.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d, 3.0d))
            ),
            new HasValue<>(1.0d)
        ).affirm();
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
