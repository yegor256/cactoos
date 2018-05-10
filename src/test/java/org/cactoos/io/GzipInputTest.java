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

import java.io.EOFException;
import java.util.zip.GZIPInputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.io.GzipInput}.
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class GzipInputTest {

    @Test
    public void readFromGzipInput() throws Exception {
        final byte[] bytes = {
            (byte) GZIPInputStream.GZIP_MAGIC,
            // @checkstyle MagicNumberCheck (1 line)
            (byte) (GZIPInputStream.GZIP_MAGIC >> 8), (byte) 0x08,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0xF3, (byte) 0x48, (byte) 0xCD,
            (byte) 0xC9, (byte) 0xC9, (byte) 0x57, (byte) 0x04, (byte) 0x00,
            (byte) 0x56, (byte) 0xCC, (byte) 0x2A, (byte) 0x9D, (byte) 0x06,
            (byte) 0x00, (byte) 0x00, (byte) 0x00,
        };
        MatcherAssert.assertThat(
            "Can't read from a gzip input",
            new TextOf(
                new GzipInput(new InputOf(bytes))
            ).asString(),
            Matchers.equalTo("Hello!")
        );
    }

    @Test(expected = EOFException.class)
    public void readFromDeadGzipInput() throws Exception {
        new LengthOf(
            new GzipInput(new DeadInput())
        ).value();
    }
}
