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

package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link LengthOf}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class LengthOfTest {

    @Test
    public void lengthOfInputWithIntegerValue() {
        new Assertion<>(
            "Must calculate length of input with integer value",
            new LengthOf(
                new InputOf("Hello1")
            ).intValue(),
            new IsEqual<>(6)
        ).affirm();
    }

    @Test
    public void lengthOfInputWithDoubleValue() {
        new Assertion<>(
            "Must calculate length of input with double value",
            new LengthOf(
                new InputOf("Hello2")
            ).doubleValue(),
            new IsEqual<>(6.0)
        ).affirm();
    }

    @Test
    public void lengthOfInputWithFloatValue() {
        new Assertion<>(
            "Must calculate length of input with float value",
            new LengthOf(
                new InputOf("Hello3")
            ).floatValue(),
            new IsEqual<>(6.0f)
        ).affirm();
    }

    @Test
    public void lengthOfInputWithCustomBuffer() {
        new Assertion<>(
            "Must calculate length with custom buffer",
            new LengthOf(
                new InputOf("test buffer1"),
                1
            ).intValue(),
            new IsEqual<>(12)
        ).affirm();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthOfZeroBuffer() {
        new Assertion<>(
            "Must calculate length with buffer of 0",
            new LengthOf(
                new InputOf("test buffer2"),
                0
            ).intValue(),
            new IsEqual<>(12)
        ).affirm();
    }

    @Test
    public void lengthOfWithIntegerValue() {
        new Assertion<>(
            "Must calculate length of iterable for integer",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).intValue(),
            new IsEqual<>(4)
        ).affirm();
    }

    @Test
    public void lengthOfWithDoubleValue() {
        new Assertion<>(
            "Must calculate length of iterable for double",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).doubleValue(),
            new IsEqual<>(4.0)
        ).affirm();
    }

    @Test
    public void lengthOfWithFloatValue() {
        new Assertion<>(
            "Must calculate length of iterable for float",
            new LengthOf(
                new IterableOf<>(1, 2, 3, 4)
            ).floatValue(),
            new IsEqual<>(4.0f)
        ).affirm();
    }

    @Test
    public void lengthOfEmptyIterable() {
        new Assertion<>(
            "Must calculate length of empty iterable",
            new LengthOf(
                new IterableOf<>()
            ).intValue(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void lengthOfWithIntegerValues() {
        new Assertion<>(
            "Must calculate length of iterator for integer",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4)
            ).intValue(),
            new IsEqual<>(4)
        ).affirm();
    }

    @Test
    public void lengthOfWithDoubleNumber() {
        new Assertion<>(
            "Must calculate length of iterator for double",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4)
            ).doubleValue(),
            new IsEqual<>(4.0)
        ).affirm();
    }

    @Test
    public void lengthOfWithFloatNumber() {
        new Assertion<>(
            "Must calculate length of iterator for float",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4)
            ).floatValue(),
            new IsEqual<>(4.0f)
        ).affirm();
    }

    @Test
    public void lengthOfEmptyIterator() {
        new Assertion<>(
            "Must calculate length of empty iterator",
            new LengthOf(
                new ListOf<>()
            ).intValue(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void lengthOfEmptyText() {
        new Assertion<>(
            "Must calculate length of empty string",
            new LengthOf(new TextOf("")).intValue(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void lengthOfUnicode() {
        new Assertion<>(
            "Must calculate length of empty string",
            new LengthOf(new TextOf("привет")).intValue(),
            new IsEqual<>(12)
        ).affirm();
    }

    @Test
    public void lengthOfText() {
        final Number num = new LengthOf(new TextOf("abcd"));
        new Assertion<>(
            "Must calculate length of non-empty string",
            new IterableOf<Number>(
                num.intValue(),
                num.floatValue(),
                num.longValue(),
                num.doubleValue()
            ),
            new HasValues<>(4, 4f, 4L, 4d)
        ).affirm();
    }
}
