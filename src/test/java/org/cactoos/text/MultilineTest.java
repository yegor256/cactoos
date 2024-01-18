/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
 * Test case for {@link Multiline}.
 * @since 0.56.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class MultilineTest {
    @Test
    void multilineAnEmptyText() {
        final String content = "";
        new Assertion<>(
            "Must multiline an empty text",
            new Multiline(content, 8),
            new IsEqual<>(new IterableOf<>(new TextOf(content)))
        ).affirm();
    }

    @Test
    void multilineText() {
        final Text content = new Joined(
            " ",
            "Lorem ea et aliquip culpa aute amet elit nostrud culpa veniam",
            "dolore eu irure incididunt. Velit officia occaecat est",
            "adipisicing mollit veniam. Minim sunt est culpa labore."
        );
        new Assertion<>(
            "Must multiline a text at limit 50 characters",
            new Multiline(content, 50),
            new IsEqual<>(
                new IterableOf<>(
                    new TextOf("Lorem ea et aliquip culpa aute amet elit nostrud"),
                    new TextOf("culpa veniam dolore eu irure incididunt. Velit"),
                    new TextOf("officia occaecat est adipisicing mollit veniam."),
                    new TextOf("Minim sunt est culpa labore.")
                )
            )
        ).affirm();
    }

    @Test
    void multilineTextOneCharSmaller() {
        final String msg = "Hello World!";
        new Assertion<>(
            "Must multiline a text with same length",
            new Multiline(msg, 5),
            new IsEqual<>(new IterableOf<>(new TextOf("Hello"), new TextOf("World!")))
        ).affirm();
    }

    @Test
    void multilineTextWithLimitBiggerThanLength() {
        final String msg = "cactoos framework";
        new Assertion<>(
            "Must multiline a text with limit bigger than length",
            new Multiline(msg, 50),
            new IsEqual<>(new IterableOf<>(new TextOf("cactoos framework")))
        ).affirm();
    }

    @Test
    void multilineTextBiggerThanDefaultLimit() {
        new Assertion<>(
            "Must abbreviate a text bigger than default max width",
            new Multiline(
                new Joined(
                    " ",
                    "The quick brown fox jumps over the lazy black dog",
                    "and after that returned to the cave"
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    new Joined(
                        " ",
                        "The quick brown fox jumps over the lazy",
                        "black dog and after that returned to the"
                    ),
                    new TextOf("cave")
                )
            )
        ).affirm();
    }
}
