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
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
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

}
