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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import org.cactoos.Bytes;

/**
 * Encodes all origin bytes using the Zip compression algorithm.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 */
public final class BytesZip implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;

    /**
     * Compression level used by the zip algorithm.
     */
    private final int level;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param origin Origin bytes.
     */
    public BytesZip(final Bytes origin) {
        // @checkstyle MagicNumber (1 line)
        this(origin, 16 << 10, Deflater.DEFAULT_COMPRESSION);
    }

    /**
     * Ctor.
     * @param origin Origin bytes.
     * @param max Max length of the compression buffer.
     */
    public BytesZip(final Bytes origin, final int max) {
        this(origin, max, Deflater.DEFAULT_COMPRESSION);
    }

    /**
     * Ctor.
     * @param origin Origin bytes.
     * @param max Max length of the compression buffer.
     * @param lvl Compression level.
     */
    public BytesZip(final Bytes origin, final int max, final int lvl) {
        this.origin = origin;
        this.size = max;
        this.level = lvl;
    }

    @Override
    public byte[] asBytes() throws IOException {
        final byte[] input = this.origin.asBytes();
        final byte[] buf = new byte[this.size];
        final Deflater compresser = new Deflater(this.level);
        compresser.setInput(input);
        compresser.finish();
        try (
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(
                input.length
            )
        ) {
            while (!compresser.finished()) {
                final int len = compresser.deflate(buf);
                baos.write(buf, 0, len);
            }
            compresser.end();
            return baos.toByteArray();
        }
    }
}
