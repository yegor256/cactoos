/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.number;

import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsNumber;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NumberOf}.
 *
 * @since 1.0.0
 */
final class NumberOfTest {

    @Test
    void parsesFloat() {
        new Assertion<>(
            "Must parse float number",
            new NumberOf("1656.894").floatValue(),
            new IsNumber(1656.894F)
        ).affirm();
    }

    @Test
    void failsIfTextDoesNotRepresentAFloat() {
        new Assertion<>(
            "Must fail parsing random text as float",
            () -> new NumberOf("abcfds").floatValue(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

    @Test
    void parsesLong() {
        new Assertion<>(
            "Must parse long number",
            new NumberOf("186789235425346").longValue(),
            new IsNumber(186_789_235_425_346L)
        ).affirm();
    }

    @Test
    void failsIfTextDoesNotRepresentALong() {
        new Assertion<>(
            "Must fail parsing random text as long",
            () -> new NumberOf("abcddd").longValue(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

    @Test
    void parsesInteger() {
        new Assertion<>(
            "Must parse integer number",
            new NumberOf("1867892354").intValue(),
            new IsNumber(1_867_892_354)
        ).affirm();
    }

    @Test
    void failsIfTextDoesNotRepresentAnInt() {
        new Assertion<>(
            "Must fail parsing random text as int",
            () -> new NumberOf("abc fdsf").intValue(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

    @Test
    void parsesDouble() {
        new Assertion<>(
            "Must parse double number",
            new NumberOf("185.65156465123").doubleValue(),
            new IsNumber(185.651_564_651_23)
        ).affirm();
    }

    @Test
    void failsIfTextDoesNotRepresentADouble() {
        new Assertion<>(
            "Must fail parsing random text as double",
            () -> new NumberOf("abfdsc").doubleValue(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

    @Test
    void parsesValueInt() {
        new Assertion<>(
            "Must parse into int",
            new NumberOf("185").intValue(),
            new IsNumber(185)
        ).affirm();
    }

    @Test
    void parsesValueIntFromText() {
        new Assertion<>(
            "Must parse from text",
            new NumberOf(new TextOf("186")).intValue(),
            new IsNumber(186)
        ).affirm();
    }
}
