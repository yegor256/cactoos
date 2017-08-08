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
package org.cactoos.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;

/**
 * Reader as {@link Bytes}.
 *
 * <p>This class is for internal use only. Use {@link BytesOf} instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.12
 */
final class ReaderAsBytes implements Bytes {

    /**
     * The reader.
     */
    private final Reader reader;

    /**
     * The charset.
     */
    private final CharSequence charset;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    ReaderAsBytes(final Reader rdr) {
        this(rdr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     */
    ReaderAsBytes(final Reader rdr, final Charset cset) {
        // @checkstyle MagicNumber (1 line)
        this(rdr, cset, 16 << 10);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @since 0.13.2
     */
    ReaderAsBytes(final Reader rdr, final CharSequence cset) {
        // @checkstyle MagicNumber (1 line)
        this(rdr, cset, 16 << 10);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    ReaderAsBytes(final Reader rdr, final Charset cset, final int max) {
        this(rdr, cset.name(), max);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @since 0.13.3
     */
    ReaderAsBytes(final Reader rdr, final int max) {
        this(rdr, StandardCharsets.UTF_8, max);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    ReaderAsBytes(final Reader rdr, final CharSequence cset, final int max) {
        this.reader = rdr;
        this.charset = cset;
        this.size = max;
    }

    @Override
    public byte[] asBytes() throws IOException {
        final char[] buffer = new char[this.size];
        final StringBuilder builder = new StringBuilder(this.size);
        while (true) {
            final int done = this.reader.read(buffer, 0, buffer.length);
            if (done < 0) {
                break;
            }
            builder.append(buffer, 0, done);
        }
        return builder.toString().getBytes(this.charset.toString());
    }

}
