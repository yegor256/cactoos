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
package org.cactoos.io;

import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test cases for {@link HeadInputStream}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class HeadInputStreamTest {

    @Test
    void testSkippingLessThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingLessThanTotal").stream(),
            5
        );
        final long skipped = stream.skip(3L);
        new Assertion<>(
            "Incorrect number of bytes skipped",
            skipped,
            new IsEqual<>(3L)
        ).affirm();
        new Assertion<>(
            "Incorrect head of the input stream has been read",
            new InputOf(stream),
            new HasContent("tS")
        ).affirm();
    }

    @Test
    void testSkippingMoreThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingMoreThanTotal").stream(),
            5
        );
        final long skipped = stream.skip(7L);
        new Assertion<>(
            "Incorrect number of bytes skipped",
            skipped,
            new IsEqual<>(5L)
        ).affirm();
        final String input = new TextOf(stream).asString();
        new Assertion<>(
            "The result text wasn't empty",
            new TextOf(input),
            new IsText("")
        ).affirm();
    }

    @Test
    void testResetting() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testResetting").stream(),
            5
        );
        final long skipped = stream.skip(7L);
        new Assertion<>(
            "Incorrect number of bytes skipped",
            skipped,
            new IsEqual<>(5L)
        ).affirm();
        stream.reset();
        new Assertion<>(
            "Reset didn't change the state",
            new InputOf(stream),
            new HasContent("testR")
        ).affirm();
    }

    @Test
    void testAvailableLessThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testAvailableLessThanTotal").stream(),
            5
        );
        new Assertion<>(
            "must count available bytes",
            stream.available(),
            new IsEqual<>(5)
        ).affirm();
    }
}
