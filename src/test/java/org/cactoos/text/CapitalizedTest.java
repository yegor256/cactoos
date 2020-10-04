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
 * Test case for {@link Capitalized}.
 * @since 0.46
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class CapitalizedTest {

    @Test
    void capitalizeEmptyText() {
        new Assertion<>(
            "Must capitalize an empty text",
            new Capitalized(new TextOf("")),
            new TextIs("")
        ).affirm();
    }

    @Test
    void capitalizeSingleLowerCaseText() {
        new Assertion<>(
            "Must capitalize single lower case text",
            new Capitalized(new TextOf("f")),
            new TextIs("F")
        ).affirm();
    }

    @Test
    void capitalizeSingleUpperCaseText() {
        new Assertion<>(
            "Must capitalize single upper case text",
            new Capitalized(new TextOf("F")),
            new TextIs("F")
        ).affirm();
    }

    @Test
    void capitalizeTextStartingWithUpperCaseCharacter() {
        new Assertion<>(
            "Must capitalize text starting with upper case character",
            new Capitalized("Bar"),
            new TextIs("Bar")
        ).affirm();
    }

    @Test
    void capitalizeTextStartingWithLowerCaseCharacter() {
        new Assertion<>(
            "Must capitalize text starting with lower case character",
            new Capitalized(new TextOf("xyz")),
            new TextIs("Xyz")
        ).affirm();
    }

    @Test
    void capitalizeTextWithUnicodeCharacter() {
        new Assertion<>(
            "Must capitalize unicode character ǆ",
            new Capitalized("ǆ"),
            new TextIs("ǅ")
        ).affirm();
    }

    @Test
    void capitalizeString() {
        new Assertion<>(
            "Must capitalize string",
            new Capitalized("foo"),
            new TextIs("Foo")
        ).affirm();
    }
}
