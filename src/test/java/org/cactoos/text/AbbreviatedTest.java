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

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Abbreviated}.
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class AbbreviatedTest {

    @Test
    void abbreviatesAnEmptyText() {
        final String msg = "";
        new Assertion<>(
            "Must abbreviate an msg text",
            new Abbreviated(msg, 8),
            new IsText(msg)
        ).affirm();
    }

    @Test
    void abbreviatesText() {
        new Assertion<>(
            "Must abbreviate a text",
            new Abbreviated("hello world", 8),
            new IsText("hello...")
        ).affirm();
    }

    @Test
    void abbreviatesTextOneCharSmaller() {
        new Assertion<>(
            "Must abbreviate a text one char smaller",
            new Abbreviated("oo programming", 10),
            new IsText("oo prog...")
        ).affirm();
    }

    @Test
    void abbreviatesTextWithSameLength() {
        final String msg = "elegant objects";
        new Assertion<>(
            "Must abbreviate a text with same length",
            new Abbreviated(msg, 15),
            new IsText(msg)
        ).affirm();
    }

    @Test
    void abbreviatesTextOneCharBigger() {
        final String msg = "the old mcdonald";
        new Assertion<>(
            "Must abbreviate a text one char bigger",
            new Abbreviated(msg, 17),
            new IsText(msg)
        ).affirm();
    }

    @Test
    void abbreviatesTextTwoCharsBigger() {
        final String msg = "hi everybody!";
        new Assertion<>(
            "Must abbreviate a text two chars bigger",
            new Abbreviated(msg, 15),
            new IsText(msg)
        ).affirm();
    }

    @Test
    void abbreviatesTextWithWidthBiggerThanLength() {
        final String msg = "cactoos framework";
        new Assertion<>(
            "Must abbreviate a text with width bigger than length",
            new Abbreviated(msg, 50),
            new IsText(msg)
        ).affirm();
    }

    @Test
    void abbreviatesTextBiggerThanDefaultMaxWidth() {
        new Assertion<>(
            "Must abbreviate a text bigger than default max width",
            new Abbreviated(
                // @checkstyle LineLengthCheck (1 line)
                "The quick brown fox jumps over the lazy black dog and after that returned to the cave"
            ),
            new IsText(
                // @checkstyle LineLengthCheck (1 line)
                "The quick brown fox jumps over the lazy black dog and after that returned to ..."
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void abbreviatesTextThatChanges() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Text txt = new TextOf(
            () -> {
                final String result;
                if (counter.getAndIncrement() == 0) {
                    result = "The quick brown fox jumps";
                } else {
                    result = "The lazy black dog";
                }
                return result;
            }
        );
        new Assertion<>(
            "Must abbreviate a text that changes",
            new Abbreviated(
                txt,
                15
            ),
            new AllOf<>(
                new IterableOf<>(
                    new IsText(
                        "The quick br..."
                    ),
                    new IsText(
                        "The lazy bla..."
                    )
                )
            )
        ).affirm();
    }
}
