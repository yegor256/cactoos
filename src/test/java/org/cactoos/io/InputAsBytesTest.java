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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.iterable.Endless;
import org.cactoos.iterable.HeadOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link InputAsBytes}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class InputAsBytesTest {

    @Test
    public void readsLargeInMemoryContent() throws Exception {
        final int multiplier = 5_000;
        final String body = "1234567890";
        MatcherAssert.assertThat(
            "Can't read large content from in-memory Input",
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
            Matchers.equalTo(body.length() * multiplier)
        );
    }

    @Test
    // @checkstyle AnonInnerLengthCheck (100 lines)
    public void readsLargeContent() throws Exception {
        final int size = 100_000;
        try (final InputStream slow = new SlowInputStream(size)) {
            MatcherAssert.assertThat(
                "Can't read large content from Input",
                new InputAsBytes(
                    new InputOf(slow)
                ).asBytes().length,
                Matchers.equalTo(size)
            );
        }
    }

    @Test
    public void readsInputIntoBytes() throws Exception {
        MatcherAssert.assertThat(
            "Can't read bytes from Input",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, друг!")
                        )
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("Hello, "),
                Matchers.endsWith("друг!")
            )
        );
    }

    @Test
    public void readsInputIntoBytesWithSmallBuffer() throws Exception {
        MatcherAssert.assertThat(
            "Can't read bytes from Input with a small reading buffer",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new BytesOf(
                            new TextOf("Hello, товарищ!")
                        )
                    ),
                    2
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("Hello,"),
                Matchers.endsWith("товарищ!")
            )
        );
    }

}
