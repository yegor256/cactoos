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
package org.cactoos.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.cactoos.Bytes;
import org.cactoos.Codec;
import org.cactoos.Text;
import org.cactoos.text.ArrayAsBytes;

/**
 * Gzip codec.
 * <p>
 * <p>The class is immutable and thread-safe.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class GzipCodec implements Codec {

    /**
     * Original codec.
     */
    private final Codec origin;

    /**
     * Ctor.
     * @param codec Original
     */
    public GzipCodec(final Codec codec) {
        this.origin = codec;
    }

    @Override
    public Bytes encode(final Text input) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final GZIPOutputStream gzip = new GZIPOutputStream(out);
        try {
            gzip.write(this.origin.encode(input).asBytes());
        } finally {
            gzip.close();
        }
        return new ArrayAsBytes(out.toByteArray());
    }

    @Override
    public Text decode(final Bytes bytes) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final InputStream gzip = new GZIPInputStream(
            new ByteArrayInputStream(bytes.asBytes())
        );
        try {
            while (true) {
                final int data = gzip.read();
                if (data < 0) {
                    break;
                }
                out.write(data);
            }
        } finally {
            gzip.close();
        }
        return this.origin.decode(new ArrayAsBytes(out.toByteArray()));
    }

}
