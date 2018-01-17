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
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.cactoos.Bytes;

/**
 * Decodes all origin bytes using the Zip compression algorithm.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.28
 */
public final class ZipBytes implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param origin Origin bytes.
     */
    public ZipBytes(final Bytes origin) {
        // @checkstyle MagicNumber (1 line)
        this(origin, 16 << 10);
    }

    /**
     * Ctor.
     * @param origin Origin bytes.
     * @param max Max length of the compression buffer.
     */
    public ZipBytes(final Bytes origin, final int max) {
        this.origin = origin;
        this.size = max;
    }

    @Override
    public byte[] asBytes() throws IOException {
        final byte[] input = this.origin.asBytes();
        final byte[] buf = new byte[this.size];
        final Inflater decompresser = new Inflater();
        decompresser.setInput(input);
        try (
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(
                input.length
            )
        ) {
            while (!decompresser.finished()) {
                final int len = decompresser.inflate(buf);
                baos.write(buf, 0, len);
            }
            decompresser.end();
            return baos.toByteArray();
        } catch (final DataFormatException ex) {
            throw new IOException(ex);
        }
    }
}
