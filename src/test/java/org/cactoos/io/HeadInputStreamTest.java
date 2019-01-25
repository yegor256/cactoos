/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextHasString;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test cases for {@link HeadInputStream}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class HeadInputStreamTest {

    @Test
    public void testSkippingLessThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingLessThanTotal").stream(),
            5
        );
        new Assertion<>(
            "Wrong number of bytes skipped",
            () -> stream.skip(3L),
            new IsEqual<>(
                3L
            )
        ).affirm();
        new Assertion<>(
            "Incorrect head of the input stream has been read",
            () -> new TextOf(
                new Sticky(() -> stream)
            ),
            new TextHasString("tS")
        ).affirm();
    }

    @Test
    public void testSkippingMoreThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingMoreThanTotal").stream(),
            5
        );
        new Assertion<>(
            "Wrong number of bytes skipped",
            () -> stream.skip(7L),
            new IsEqual<>(
                0L
            )
        ).affirm();
        final String input = new TextOf(stream).asString();
        new Assertion<>(
            "The result text wasn't empty",
            () -> new TextOf(input),
            new TextIs("")
        ).affirm();
    }

    @Test
    public void testResetting() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testResetting").stream(),
            5
        );
        MatcherAssert.assertThat(
            stream.skip(7L),
            new IsEqual<>(
                5L
            )
        );
        stream.reset();
        new Assertion<>(
            "Reset didn't change the state",
            () -> new TextOf(
                new Sticky(() -> stream)
            ),
            new TextHasString("testR")
        ).affirm();
    }

    @Test
    public void testAvailableLessThanTotal() throws Exception {
        final HeadInputStream stream = new HeadInputStream(
            new InputOf("testAvailableLessThanTotal").stream(),
            5
        );
        MatcherAssert.assertThat(
            "Count of available bytes is incorrect",
            stream.available(),
            new IsEqual<>(5)
        );
    }
}
