/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test of range implementation.
 *
 * @since 1.0
 */
final class RangeOfTest {

    @Test
    void testIntegerRange() {
        new Assertion<>(
            "Must generate a range of integers",
            new ListOf<>(
                new RangeOf<>(1, 5, value -> value + 1)
            ),
            new HasValues<>(1, 2, 3, 4, 5)
        ).affirm();
    }

    @Test
    void testIntegerFibonacciRange() {
        final AtomicReference<Integer> last = new AtomicReference<>(0);
        new Assertion<>(
            "Must generate a range of fibonacci integers",
            new ListOf<>(
                new RangeOf<>(
                    1,
                    100,
                    input -> {
                        final int next = last.get() + input;
                        last.set(input);
                        return next;
                    }
                )
            ),
            new HasValues<>(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
        ).affirm();
    }

    @Test
    void testLongRange() {
        new Assertion<>(
            "Must generate a range of long",
            new ListOf<>(
                new RangeOf<>(1L, 5L, value -> value + 1L)
            ),
            new HasValues<>(1L, 2L, 3L, 4L, 5L)
        ).affirm();
    }

    @Test
    void testCharacterRange() {
        new Assertion<>(
            "Must generate a range of characters.",
            new ListOf<>(
                new RangeOf<>('a', 'c', value -> (char) (value + 1))
            ),
            new HasValues<>('a', 'b', 'c')
        ).affirm();
    }

    @Test
    void testLocalDateRange() {
        new Assertion<>(
            "Must generate a range of local dates.",
            new ListOf<>(
                new RangeOf<>(
                    LocalDate.of(2017, 1, 1),
                    LocalDate.of(2017, 1, 3),
                    value -> value.plus(1L, ChronoUnit.DAYS)
                )
            ),
            new HasValues<>(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2017, 1, 2),
                LocalDate.of(2017, 1, 3)
            )
        ).affirm();
    }

}
