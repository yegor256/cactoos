/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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

import org.cactoos.text.ComparableText;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Equals}.
 *
 * @since 0.9
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class EqualsTest {

    @Test
    void compareEquals() {
        new Assertion<>(
            "Must compare if two integers are equal",
            new Equals<>(1, 1),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void compareNotEquals() {
        new Assertion<>(
            "Must compare if two integers are not equal",
            new Equals<>(1, 2),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void compareEqualsTextScalar() {
        final String str = "hello";
        new Assertion<>(
            "Must compare if two strings are equal",
            new Equals<>(str, str),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void compareNotEqualsTextScalar() {
        new Assertion<>(
            "Must compare if two strings are not equal",
            new Equals<>("world", "worle"),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void compareText() {
        new Assertion<>(
            "Must compare if two comparable test are equal, see #1174",
            new Equals<>(
                new ComparableText(new TextOf("hello")),
                new ComparableText(new TextOf("hello"))
            ),
            new HasValue<>(true)
        ).affirm();
    }
}
