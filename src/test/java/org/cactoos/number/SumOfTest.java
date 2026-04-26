/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.Joined;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link SumOf}.
 * @since 0.9
 * @todo #1335:30min Following the rewrite of the Number implementations
 *  in cactoos, the three methods below prefixed by overflow where adapted
 *  because they started to give different results. The task is to investigate
 *  thoroughly what is a "correct" behaviour concerning overflow in the case
 *  of SumOf and adapt the tests and the code to make this obvious and clear.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SumOfTest {

    @Test
    void withListOfNumbersInt() {
        MatcherAssert.assertThat(
            "sum of integers should be calculated as integer",
            new SumOf(1, 2, 3).intValue(),
            new IsEqual<>(6)
        );
    }

    @Test
    void withListOfNumbersDouble() {
        MatcherAssert.assertThat(
            "sum of doubles should be calculated as double",
            new SumOf(1.0d, 2.0d, 3.0d).doubleValue(),
            new IsEqual<>(6.0d)
        );
    }

    @Test
    void withListOfNumbersFloat() {
        MatcherAssert.assertThat(
            "sum of floats should be calculated as float",
            new SumOf(1.0f, 2.0f, 3.0f).floatValue(),
            new IsEqual<>(6.0f)
        );
    }

    @Test
    void withListOfNumbersLong() {
        MatcherAssert.assertThat(
            "sum of longs should be calculated as long",
            new SumOf(1L, 2L, 3L).longValue(),
            new IsEqual<>(6L)
        );
    }

    @Test
    void withCollectionLong() {
        MatcherAssert.assertThat(
            "sum of longs should be calculated as long",
            new SumOf(
                new IterableOf<>(1, 2, 3, 4)
            ).longValue(),
            new IsEqual<>(10L)
        );
    }

    @Test
    void withCollectionInt() {
        MatcherAssert.assertThat(
            "sum of longs should be calculated as integer",
            new SumOf(
                new IterableOf<>(1L, 2L, 3L, 4L)
            ).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void withCollectionFloat() {
        MatcherAssert.assertThat(
            "sum of floats should be calculated as float",
            new SumOf(
                new IterableOf<>(1.0f, 2.0f, 3.0f, 4.0f)
            ).floatValue(),
            new IsEqual<>(10.0f)
        );
    }

    @Test
    void withCollectionDouble() {
        MatcherAssert.assertThat(
            "sum of double should be calculated as double",
            new SumOf(
                new IterableOf<>(1.0d, 2.0d, 3.0d, 4.0d)
            ).doubleValue(),
            new IsEqual<>(10.0d)
        );
    }

    @Test
    void withIterableOfInts() {
        MatcherAssert.assertThat(
            "sum of integers should be calculated as integer",
            new SumOf(new ListOf<>(1, 2, 3, 4)).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowIntFromLongValues() {
        MatcherAssert.assertThat(
            "sum of longs should be calculated as integer without overflow error",
            new SumOf(2_147_483_647L + 1L << 1, 10L).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowIntFromLongValuesIncludingNegative() throws Exception {
        MatcherAssert.assertThat(
            new Joined(
                "",
                "sum of positive and negative longs should be calculated",
                "as integer without overflow error"
            ).asString(),
            new SumOf(
                2_147_483_647L + 1L << 1,
                10L,
                -(2_147_483_647L + 1L) << 1
            ).intValue(),
            new IsEqual<>(10)
        );
    }

    @Test
    void overflowFloatFromLongValues() {
        MatcherAssert.assertThat(
            "sum of longs should be calculated as float",
            new SumOf(2_147_483_647L + 1L << 1, 10L).floatValue(),
            new IsEqual<>(4_294_967_300.0f)
        );
    }
}
