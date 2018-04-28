/**
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

package org.cactoos.bytes;

import java.io.IOException;
import java.util.Arrays;
import org.cactoos.io.BytesOf;
import org.cactoos.matchers.MatcherOf;
import org.cactoos.text.HexOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link HexBytes}.
 *
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle MagicNumberCheck (500 line)
 * @checkstyle JavadocMethodCheck (500 line)
 */
public final class HexBytesTest {

    @Test
    public void empytText() throws IOException {
        MatcherAssert.assertThat(
            "Can't represent an empty hexadecimal text",
            new HexBytes(new TextOf("")).asBytes(),
            new MatcherOf<>(array -> array.length == 0)
        );
    }

    @Test
    public void validHex() throws IOException {
        final byte[] bytes = new byte[256];
        for (int index = 0; index < 256; ++index) {
            bytes[index] = (byte) (index + Byte.MIN_VALUE);
        }
        MatcherAssert.assertThat(
            "Can't convert hexadecimal text to bytes",
            new HexBytes(
                new HexOf(
                    new BytesOf(bytes)
                )
            ).asBytes(),
            new MatcherOf<>(
                (byte[] array) -> Arrays.equals(bytes, array)
            )
        );
    }

    @Test(expected = IOException.class)
    public void invalidHexLength() throws IOException {
        new HexBytes(new TextOf("ABF")).asBytes();
    }

    @Test(expected = IOException.class)
    public void invalidHex() throws IOException {
        new HexBytes(new TextOf("ABG!")).asBytes();
    }
}
