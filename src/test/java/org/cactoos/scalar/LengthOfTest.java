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

import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOf}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class LengthOfTest {

    @Test
    public void lengthOfInputWithIntegerValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of input with integer value",
            new LengthOf(
                new InputOf("Hello1")
            ).intValue(),
            Matchers.equalTo(6)
        );
    }

    @Test
    public void lengthOfInputWithDoubleValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of input with double value",
            new LengthOf(
                new InputOf("Hello2")
            ).doubleValue(),
            Matchers.equalTo(6.0)
        );
    }

    @Test
    public void lengthOfInputWithFloatValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of input with float value",
            new LengthOf(
                new InputOf("Hello3")
            ).floatValue(),
            Matchers.equalTo(6.0f)
        );
    }

    @Test
    public void lengthOfInputWithCustomBuffer() {
        MatcherAssert.assertThat(
            "Can't calculate length with custom buffer",
            new LengthOf(
                new InputOf("test buffer1"),
                1
            ).intValue(),
            Matchers.equalTo(12)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthOfZeroBuffer() {
        MatcherAssert.assertThat(
            "Can't calculate length with buffer of 0",
            new LengthOf(
                new InputOf("test buffer2"),
                0
            ).intValue(),
            Matchers.equalTo(12)
        );
    }

    @Test
    public void lengthOfWithIntegerValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterable for integer",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void lengthOfWithDoubleValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterable for double",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).doubleValue(),
            Matchers.equalTo(4.0)
        );
    }

    @Test
    public void lengthOfWithFloatValue() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterable for float",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).floatValue(),
            Matchers.equalTo(4.0f)
        );
    }

    @Test
    public void lengthOfEmptyIterable() {
        MatcherAssert.assertThat(
            "Can't calculate length of empty iterable",
            new LengthOf(
                new IterableOf<>()
            ).intValue(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void lengthOfWithIntegerValues() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for integer",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void lengthOfWithDoubleNumber() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for double",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).doubleValue(),
            Matchers.equalTo(4.0)
        );
    }

    @Test
    public void lengthOfWithFloatNumber() {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for float",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).floatValue(),
            Matchers.equalTo(4.0f)
        );
    }

    @Test
    public void lengthOfEmptyIterator() {
        MatcherAssert.assertThat(
            "Can't calculate length of empty iterator",
            new LengthOf(
                new ListOf<>().iterator()
            ).intValue(),
            Matchers.equalTo(0)
        );
    }
}
