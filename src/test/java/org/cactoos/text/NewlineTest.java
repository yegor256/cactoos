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

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for {@link Newline}.
 * @since 0.50
 */
final class NewlineTest {
    @Test
    void testEmptyNewline() {
        new Assertion<>(
            "Must be equal to the System.lineSeparator()",
            new Newline(),
            new IsText(System.lineSeparator())
        ).affirm();
    }

    @Test
    void testStringIntoNewline() {
        final String text = UUID.randomUUID().toString();
        new Assertion<>(
            "must be equal to System.lineSeparator() plus the provided plain String value",
            new Newline(text),
            new IsText(this.expectedValue(text))
        ).affirm();
    }

    @Test
    void testTextIntoNewline() {
        final String text = UUID.randomUUID().toString();
        new Assertion<>(
            "must be equal to System.lineSeparator() plus the provided Text's value",
            new Newline(new TextOf(text)),
            new IsText(this.expectedValue(text))
        ).affirm();
    }

    private String expectedValue(final String text) {
        return String.format("%n%1$s", text);
    }
}
