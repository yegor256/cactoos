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
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Split}.
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SplitTest {

    @Test
    void splitStringWithStringRegex() {
        new Assertion<>(
            "Must split string with string regex",
            new Split("Hello world!", "\\s+"),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello"), new TextOf("world!")))
        ).affirm();
    }

    @Test
    void splitStringWithTextRegex() {
        new Assertion<>(
            "Must split string with text regex",
            new Split("Cactoos OOP!", new TextOf("\\s")),
            new IsEqual<>(new IterableOf<>(new TextOf("Cactoos"), new TextOf("OOP!")))
        ).affirm();
    }

    @Test
    void splitTextWithStringRegex() {
        new Assertion<>(
            "Must split text with string regex",
            new Split(new TextOf("Cact4Primitives!"), "\\d+"),
            new IsEqual<>(new IterableOf<>(new TextOf("Cact"), new TextOf("Primitives!")))
        ).affirm();
    }

    @Test
    void splitTextWithTextRegex() {
        new Assertion<>(
            "Must split text with text regex",
            new Split(new TextOf("Split#OOP"), new TextOf("#")),
            new IsEqual<>(new IterableOf<>(new TextOf("Split"), new TextOf("OOP")))
        ).affirm();
    }

    @Test
    void splitStringWithStringRegexAndLimit() {
        new Assertion<>(
            "Must split string with string regex and limit",
            new Split("Hello! ! world!", " ", 2),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello!"), new TextOf("! world!")))
        ).affirm();
    }

    @Test
    void splitStringWithTextRegexAndLimit() {
        new Assertion<>(
            "Must split string with text regex and limit",
            new Split("Cactoos! ! OOP!", new TextOf(" "), 2),
            new IsEqual<>(new IterableOf<>(new TextOf("Cactoos!"), new TextOf("! OOP!")))
        ).affirm();
    }

    @Test
    void splitTextWithStringRegexAndLimit() {
        final Text txt = new TextOf("Cact!4Primitives");
        new Assertion<>(
            "Must split text with string regex and limit",
            new Split(txt, "4", 1),
            new IsEqual<>(new IterableOf<>(txt))
        ).affirm();
    }

    @Test
    void splitTextWithTextRegexAndLimit() {
        final Text txt = new TextOf("Split!# #OOP");
        new Assertion<>(
            "Must split text with text regex and limit",
            new Split(txt, "\\W+", 1),
            new IsEqual<>(new IterableOf<>(txt))
        ).affirm();
    }

    @Test
    void splitWithZeroLimit() {
        new Assertion<>(
            "Must split string with string regex and zero limit",
            new Split("Hello. The! !world", " +", 0),
            new IsEqual<>(
                new IterableOf<>(new TextOf("Hello."), new TextOf("The!"), new TextOf("!world"))
            )
        ).affirm();
    }

    @Test
    void splitWithNegativeLimit() {
        new Assertion<>(
            "Must split string with string regex and negative limit",
            new Split("Hello: The world", " ", -1),
            new IsEqual<>(
                new IterableOf<>(new TextOf("Hello:"), new TextOf("The"), new TextOf("world"))
            )
        ).affirm();
    }
}
