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

package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link org.cactoos.io.GzipOutput}.
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class GzipOutputTest {
    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void writeToGzipOutput() throws Exception {
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
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new LengthOf(
            new TeeInput(
                "Hello!",
                new GzipOutput(new OutputTo(baos))
            )
        ).value();
        MatcherAssert.assertThat(
            "Can't write to a gzip output",
            baos.toByteArray(),
            Matchers.equalTo(bytes)
        );
    }

    @Test(expected = IOException.class)
    public void writeToClosedGzipOutput() throws Exception {
        final OutputStream stream = new FileOutputStream(
            this.folder.newFile("cactoos.txt")
        );
        stream.close();
        new LengthOf(
            new TeeInput(
                "Hello!",
                new GzipOutput(new OutputTo(stream))
            )
        ).value();
    }
}
