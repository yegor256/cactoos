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
package org.cactoos.io;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.iterable.Endless;
import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link InputAsBytes}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("unchecked")
final class InputAsBytesTest {

    @Test
    void readsLargeInMemoryContent() throws Exception {
        final int multiplier = 5_000;
        final String body = "1234567890";
        new Assertion<>(
            "must read large content from in-memory Input",
            new InputAsBytes(
                new InputOf(
                    String.join(
                        "",
                        new HeadOf<>(
                            multiplier, new Endless<>(body)
                        )
                    )
                )
            ).asBytes().length,
            new IsEqual<>(body.length() * multiplier)
        ).affirm();
    }

    @Test
    // @checkstyle AnonInnerLengthCheck (100 lines)
    void readsLargeContent() throws Exception {
        final int size = 100_000;
        try (InputStream slow = new SlowInputStream(size)) {
            new Assertion<>(
                "must read large content from Input",
                new InputAsBytes(
                    new InputOf(slow)
                ).asBytes().length,
                new IsEqual<>(size)
            ).affirm();
        }
    }

    @Test
    void readsInputIntoBytes() throws Exception {
        new Assertion<>(
            "must read bytes from Input",
            new TextOf(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, друг!")
                        )
                    )
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith("Hello, "),
                    new EndsWith("друг!")
                )
            )
        ).affirm();
    }

    @Test
    void readsInputIntoBytesWithSmallBuffer() throws Exception {
        new Assertion<>(
            "must read bytes from Input with a small reading buffer",
            new TextOf(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, товарищ!")
                        )
                    ),
                    2
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith("Hello,"),
                    new EndsWith("товарищ!")
                )
            )
        ).affirm();
    }

}
