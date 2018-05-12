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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.TextHasString;

/**
 * Test case for {@link RotatedText}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RotatedTextTest {

    @Test
    public void rotateRightText() {
        MatcherAssert.assertThat(
            "Can't rotate text to right",
            new RotatedText(
                new TextOf("Hello!"), 2
            ),
            new TextHasString("o!Hell")
        );
    }

    @Test
    public void rotateLeftText() {
        MatcherAssert.assertThat(
            "Can't rotate text to left",
            new RotatedText(
                new TextOf("Hi!"), -1
            ),
            new TextHasString("i!H")
        );
    }

    @Test
    public void noRotateWhenShiftZero() {
        final String nonrotate = "Cactoos!";
        MatcherAssert.assertThat(
            "Rotate text shift zero",
            new RotatedText(
                new TextOf(nonrotate), 0
            ),
            new TextHasString(nonrotate)
        );
    }

    @Test
    public void noRotateWhenShiftModZero() {
        final String nonrotate = "Rotate";
        MatcherAssert.assertThat(
            "Rotate text shift mod zero",
            new RotatedText(
                new TextOf(nonrotate), nonrotate.length()
            ),
            new TextHasString(nonrotate)
        );
    }

    @Test
    public void noRotateWhenEmpty() {
        MatcherAssert.assertThat(
            "Rotate text when empty",
            new RotatedText(
                new TextOf(""), 2
            ),
            new TextHasString("")
        );
    }
}
