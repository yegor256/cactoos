/**
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
package org.cactoos.iterable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.cactoos.Func;
import org.cactoos.collection.CollectionOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test of range implementation.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public class RangeOfTest {

    @Test
    public final void testIntegerRange() {
        MatcherAssert.assertThat(
            "Can't generate a range of integers",
            new CollectionOf<>(
                new RangeOf<>(1, 5, value -> ++value)
            ),
            Matchers.contains(1, 2, 3, 4, 5)
        );
    }

    @Test
    public final void testIntegerFibonacciRange() {
        MatcherAssert.assertThat(
            "Can't generate a range of fibonacci integers",
            new CollectionOf<>(
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
        );
    }

    @Test
    public final void testLongRange() {
        MatcherAssert.assertThat(
            "Can't generate a range of long",
            new CollectionOf<>(
                new RangeOf<>(1L, 5L, value -> ++value)
            ),
            Matchers.contains(1L, 2L, 3L, 4L, 5L)
        );
    }

    @Test
    public final void testCharacterRange() {
        MatcherAssert.assertThat(
            "Can't generate a range of characters",
            new CollectionOf<>(
                new RangeOf<>('a', 'c', value -> ++value)
            ),
            Matchers.contains('a', 'b', 'c')
        );
    }

    @Test
    public final void testLocalDateRange() {
        MatcherAssert.assertThat(
            "Can't generate a range of local dates.",
            new CollectionOf<>(
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
        );
    }

}
