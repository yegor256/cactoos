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
import java.util.Date;
import java.util.NoSuchElementException;
import org.cactoos.time.DateOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LowestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class LowestOfTest {

    @Test(expected = NoSuchElementException.class)
    public void failsForEmptyIterable() throws Exception {
        new LowestOf<>(() -> Collections.emptyIterator()).value();
    }

    @Test
    public void singleAtSingleIterable() throws Exception {
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
    public void lowestIntegerAtIterable() throws Exception {
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
    public void lowestLongAtIterable() throws Exception {
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
    public void lowestDoubleAtIterable() throws Exception {
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
    public void lowestStringAtIterable() throws Exception {
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
    public void lowestCharAtIterable() throws Exception {
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
    public void lowestSumAtIterable() throws Exception {
        MatcherAssert.assertThat(
            "Can't find the lowest double sum among many",
            new LowestOf<Double>(
                new SumOf(1.0d),
                new SumOf(1.0d, 2.0d),
                new SumOf(1.0d, 2.0d, 3.0d)
            ).value(),
            Matchers.equalTo(new SumOf(1.0d).value())
        );
    }

    @Test
    public void lowestDateAtIterable() throws Exception {
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
    public void lowestBooleanAtIterable() throws Exception {
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
