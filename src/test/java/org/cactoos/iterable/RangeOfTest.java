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
package org.cactoos.iterable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import org.cactoos.Func;
import org.cactoos.list.ListOf;
import org.cactoos.proc.ForEach;
import org.cactoos.proc.RepeatedProc;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test of range implementation.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class RangeOfTest {

    @Test
    void testIntegerRange() {
        new Assertion<>(
            "Must generate a range of integers",
            new ListOf<>(
                new RangeOf<>(1, 5, value -> ++value)
            ),
            Matchers.contains(1, 2, 3, 4, 5)
        ).affirm();
    }

    @Test
    void testIntegerFibonacciRange() {
        new Assertion<>(
            "Must generate a range of fibonacci integers",
            new ListOf<>(
                new RangeOf<>(
                    1,
                    100,
                    new Func<Integer, Integer>() {
                        private int last;

                        @Override
                        public Integer apply(
                            final Integer input) throws Exception {
                            final int next = this.last + input;
                            this.last = input;
                            return next;
                        }
                    }
                )
            ),
            Matchers.contains(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
        ).affirm();
    }

    @Test
    void testLongRange() {
        new Assertion<>(
            "Must generate a range of long",
            new ListOf<>(
                new RangeOf<>(1L, 5L, value -> ++value)
            ),
            Matchers.contains(1L, 2L, 3L, 4L, 5L)
        ).affirm();
    }

    @Test
    void testCharacterRange() {
        new Assertion<>(
            "Must generate a range of characters",
            new ListOf<>(
                new RangeOf<>('a', 'c', value -> ++value)
            ),
            Matchers.contains('a', 'b', 'c')
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
            Matchers.contains(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2017, 1, 2),
                LocalDate.of(2017, 1, 3)
            )
        ).affirm();
    }

    @Test
    void shouldBeTraversableMultipleTimes() throws Exception {
        final Iterable<Character> range = new RangeOf<>('a', 'c', value -> ++value);
        final Collection<Character> copy = new ArrayList<>(6);
        new RepeatedProc<>(new ForEach<>(copy::add), 2).exec(range);
        new Assertion<>(
            "Must add elements two times",
            copy,
            new HasSize(6)
        ).affirm();
    }

    @Test
    void shouldNotChangeAfterTraversing() throws Exception {
        final Iterable<Character> range = new RangeOf<>('a', 'c', value -> ++value);
        new ForEach<>(
            (Character ignored) -> {
            }
        ).exec(range);
        new Assertion<>(
            "Must be equal",
            range,
            new IsEqual<>(
                new RangeOf<>('a', 'c', value -> ++value)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void producesChars() {
        new Assertion<>(
            "Must produce three ranges",
            new Joined<>(
                new RangeOf<>('0', '9', ch -> (char) (ch + 1)),
                new RangeOf<>('A', 'Z', ch -> (char) (ch + 1)),
                new RangeOf<>('a', 'z', ch -> (char) (ch + 1))
            ),
            new IsEqual<>(
                new IterableOfChars(
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z'
                )
            )
        ).affirm();
    }

    @Test
    void producesCharsJoined() {
        new Assertion<>(
            "Must produce correct range of characters",
            new RangeOf<>('!', '~', ch -> (char) (ch + 1)),
            new IsEqual<>(
                new IterableOfChars(
                    '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
                    '+', ',', '-', '.', '/', '0', '1', '2', '3', '4',
                    '5', '6', '7', '8', '9', ':', ';', '<', '=', '>',
                    '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\',
                    ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f',
                    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                    'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    '{', '|', '}', '~'
                )
            )
        ).affirm();
    }

}
