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
 * Test case for {@link HighestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class HighestOfTest {

    @Test
    void failsForEmptyIterable() {
        new Assertion<>(
            "Exception is expected for iterating empty collection",
            () -> new HighestOf<>(() -> Collections.emptyIterator()).value(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }

    @Test
    void singleAtSingleIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest among one by scalars",
            new HighestOf<Integer>(() -> 10).value(),
            Matchers.equalTo(10)
        );
        MatcherAssert.assertThat(
            "Can't find the highest among one",
            new HighestOf<>(10).value(),
            Matchers.equalTo(10)
        );
    }

    @Test
    void highestIntegerAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest integer among many by scalars",
            new HighestOf<Integer>(
                () -> 10,
                () -> 0,
                () -> -1,
                () -> 2
            ).value(),
            Matchers.equalTo(10)
        );
        MatcherAssert.assertThat(
            "Can't find the highest integer among many",
            new HighestOf<>(10, 0, -1, 2).value(),
            Matchers.equalTo(10)
        );
        MatcherAssert.assertThat(
            "Can't find the highest negative integer among many",
            new HighestOf<>(-272, -10, -134, -101).value(),
            Matchers.equalTo(-10)
        );
        MatcherAssert.assertThat(
            "Can't find the highest max integer among many",
            new HighestOf<>(Integer.MIN_VALUE, Integer.MAX_VALUE).value(),
            Matchers.equalTo(Integer.MAX_VALUE)
        );
    }

    @Test
    void highestLongAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest long among many by scalars",
            new HighestOf<Long>(
                () -> 10L,
                () -> 0L,
                () -> -1L,
                () -> 2L
            ).value(),
            Matchers.equalTo(10L)
        );
        MatcherAssert.assertThat(
            "Can't find the highest long among many",
            new HighestOf<>(10L, 0L, -1L, 2L).value(),
            Matchers.equalTo(10L)
        );
        MatcherAssert.assertThat(
            "Can't find the highest negative long among many",
            new HighestOf<>(-272L, -10L, -134L, -101L).value(),
            Matchers.equalTo(-10L)
        );
        MatcherAssert.assertThat(
            "Can't find the highest max integer long many",
            new HighestOf<>(Long.MIN_VALUE, Long.MAX_VALUE).value(),
            Matchers.equalTo(Long.MAX_VALUE)
        );
    }

    @Test
    void highestDoubleAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest double among many by scalars",
            new HighestOf<Double>(
                () -> 10.9,
                () -> 0.8,
                () -> -1.5,
                () -> 10.8
            ).value(),
            Matchers.equalTo(10.9)
        );
        MatcherAssert.assertThat(
            "Can't find the highest double among many",
            new HighestOf<>(10., 0., -1., 2.).value(),
            Matchers.equalTo(10.)
        );
        MatcherAssert.assertThat(
            "Can't find the highest negative double among many",
            new HighestOf<>(-272., -10., -134., -101.).value(),
            Matchers.equalTo(-10.)
        );
        MatcherAssert.assertThat(
            "Can't find the highest max double among many",
            new HighestOf<>(Double.MIN_VALUE, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the highest min double among many",
            new HighestOf<>(Double.MIN_VALUE, -10.).value(),
            Matchers.equalTo(Double.MIN_VALUE)
        );
        MatcherAssert.assertThat(
            "Can't find the highest NaN double among many",
            new HighestOf<>(Double.NaN, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.NaN)
        );
        MatcherAssert.assertThat(
            "Can't find the highest positive infinity among many",
            new HighestOf<>(Double.POSITIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.POSITIVE_INFINITY)
        );
        MatcherAssert.assertThat(
            "Can't find the highest negative infinity among many",
            new HighestOf<>(Double.NEGATIVE_INFINITY, Double.MAX_VALUE).value(),
            Matchers.equalTo(Double.MAX_VALUE)
        );
    }

    @Test
    void highestStringAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest string among many by scalars",
            new HighestOf<String>(() -> "Apple", () -> "Orange").value(),
            Matchers.equalTo("Orange")
        );
        MatcherAssert.assertThat(
            "Can't find the highest string among many",
            new HighestOf<>("Apple", "Orange").value(),
            Matchers.equalTo("Orange")
        );
    }

    @Test
    void highestCharAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest char among many by scalars",
            new HighestOf<Character>(() -> 'A', () -> 'B').value(),
            Matchers.equalTo('B')
        );
        MatcherAssert.assertThat(
            "Can't find the highest char among many",
            new HighestOf<>('A', 'B').value(),
            Matchers.equalTo('B')
        );
    }

    @Test
    void highestSumAtIterable() {
        new Assertion<>(
            "Must find the highest double sum among many",
            new HighestOf<>(
                new ComparableNumber(new SumOf(1.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d)),
                new ComparableNumber(new SumOf(1.0d, 2.0d, 3.0d))
            ),
            new HasValue<>(6.0d)
        ).affirm();
    }

    @Test
    void highestDateAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest date among many",
            new HighestOf<Date>(
                new DateOf("2007-12-26T14:20:16.000000017Z"),
                new DateOf("2017-12-13T14:15:16.000000017Z"),
                new DateOf("2017-12-13T14:15:16.000000018Z")
            ).value(),
            Matchers.equalTo(
                new DateOf("2017-12-13T14:15:16.000000018Z").value()
            )
        );
    }

    @Test
    void highestBooleanAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the highest boolean among many",
            new HighestOf<Boolean>(
                new BoolOf("false"),
                new BoolOf("true")
            ).value(),
            Matchers.equalTo(new BoolOf("true").value())
        );
    }
}
