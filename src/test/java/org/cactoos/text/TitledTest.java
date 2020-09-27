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
package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link Titled}.
 * @since 0.46
 */
public class TitledTest {

    @Test
    void titleEmptyText() {
        new Assertion<>(
            "Must title an empty text",
            new Titled(new TextOf("")),
            new TextIs("")
        ).affirm();
    }

    @Test
    void titleSingleLowerCaseText() {
        new Assertion<>(
            "Must title single lower case text",
            new Titled(new TextOf("f")),
            new TextIs("F")
        ).affirm();
    }

    @Test
    void titleSingleUpperCaseText() {
        new Assertion<>(
            "Must title single upper case text",
            new Titled(new TextOf("F")),
            new TextIs("F")
        ).affirm();
    }

    @Test
    void titleTextWithUnicodeCharacter() {
        new Assertion<>(
            "Must title unicode character ǆ",
            new Titled("ǆ"),
            new TextIs("ǅ")
        ).affirm();
    }

    @Test
    void titleString() {
        new Assertion<>(
            "Must title string",
            new Titled("foo"),
            new TextIs("FOO")
        ).affirm();
    }
}
