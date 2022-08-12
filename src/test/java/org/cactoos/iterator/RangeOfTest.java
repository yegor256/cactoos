/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
                new RangeOf<>(1, 5, value -> ++value)
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
                new RangeOf<>(1L, 5L, value -> ++value)
            ),
            new HasValues<>(1L, 2L, 3L, 4L, 5L)
        ).affirm();
    }

    @Test
    void testCharacterRange() {
        new Assertion<>(
            "Must generate a range of characters.",
            new ListOf<>(
                new RangeOf<>('a', 'c', value -> ++value)
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
                    value -> value.plus(1, ChronoUnit.DAYS)
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
