/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.cactoos.scalar.LengthOf;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextHasString;

/**
 * Test case for {@link Split}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SplitTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void splitTextLength() throws Exception {
        new Assertion<>(
            "Can't split a text. Incorrect length",
            new LengthOf(
                new Split("Hello world!", "\\s+")
            ).intValue(),
            Matchers.equalTo(2)
        ).affirm();
    }

    @Test
    public void splitTextItem() throws Exception {
        new Assertion<>(
            "Can't split a text. Incorrect item",
            new Split("Hello world! [2]", "\\s+").iterator().next(),
            new TextHasString("Hello")
        ).affirm();
    }

    @Test
    public void splitStringWithTextRegex() throws Exception {
        new Assertion<>(
            "Can't split an string with text regex",
            new Split(
                "Cactoos OOP!",
                new TextOf("\\s")
            ).iterator().next(),
            new TextHasString("Cactoos")
        ).affirm();
    }

    @Test
    public void splitTextWithStringRegex() throws Exception {
        new Assertion<>(
            "Can't split an text with string regex",
            new Split(
                new TextOf("Cact4Primitives!"),
                "\\d+"
            ).iterator().next(),
            new TextHasString("Cact")
        ).affirm();
    }

    @Test
    public void splitTextWithTextRegex() throws Exception {
        new Assertion<>(
            "Can't split an text with text regex",
            new Split(
                new TextOf("Split#OOP!"),
                "\\W+"
            ).iterator().next(),
            new TextHasString("Split")
        ).affirm();
    }
}
