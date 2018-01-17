/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import org.cactoos.io.BytesOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.bytes.ZipBytes}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 */
public final class ZipBytesTest {

    /**
     * Check bytes decodes using the Zip compression algorithm.
     * @throws IOException If fails.
     */
    @Test
    public void checkDecode() throws IOException {
        final byte[] bytes = {
            (byte) 0x78, (byte) 0x9C, (byte) 0xF3, (byte) 0x48, (byte) 0xCD,
            (byte) 0xC9, (byte) 0xC9, (byte) 0x57, (byte) 0x04, (byte) 0x00,
            (byte) 0x07, (byte) 0xA2, (byte) 0x02, (byte) 0x16,
        };
        MatcherAssert.assertThat(
            "Can't decodes bytes using the Zip compression algorithm",
            new ZipBytes(
                new BytesOf(
                    bytes
                )
            ).asBytes(),
            Matchers.equalTo(
                new BytesOf("Hello!").asBytes()
            )
        );
    }

}
