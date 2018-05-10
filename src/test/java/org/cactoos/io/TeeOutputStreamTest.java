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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TeeOutputStream}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TeeOutputStreamTest {

    @Test
    public void copiesContentByteByByte() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        final String content = "Hello, товарищ!";
        MatcherAssert.assertThat(
            "Can't copy OutputStream to OutputStream byte by byte",
            new TextOf(
                new ReaderOf(
                    new TeeInputStream(
                        new ByteArrayInputStream(
                            content.getBytes(StandardCharsets.UTF_8)
                        ),
                        new TeeOutputStream(baos, copy)
                    )
                )
            ).asString(),
            Matchers.allOf(
                Matchers.equalTo(content),
                Matchers.equalTo(
                    new String(baos.toByteArray(), StandardCharsets.UTF_8)
                ),
                Matchers.equalTo(
                    new String(copy.toByteArray(), StandardCharsets.UTF_8)
                )
            )
        );
    }

}
