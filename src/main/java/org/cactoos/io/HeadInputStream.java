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

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link InputStream} that encapsulates other sources of data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.29.3
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class HeadInputStream extends InputStream {

    /**
     * Input.
     */
    private final InputStream input;

    /**
     * Length.
     */
    private final int length;

    /**
     * Bytesread.
     */
    private int bytesread;

    /**
     * Ctor.
     * @param src Source of data
     * @param length The length
     */
    public HeadInputStream(final InputStream src, final int length) {
        super();
        this.input = src;
        this.length = length;
    }

    @Override
    public int read() throws IOException {
        int data = -1;
        if (this.bytesread < this.length) {
            data = this.input.read();
            this.bytesread += 1;
        }
        return data;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        int max = -1;
        if (this.bytesread < this.length) {
            final int predlength = this.bytesread + buf.length;
            if (predlength > this.length) {
                max =  this.read(buf, 0, this.length);
            } else {
                max =  this.read(buf, 0, buf.length);
            }
        }
        if (max != -1) {
            this.bytesread += max;
        }
        return max;
    }

    @Override
    public int read(final byte[] buf, final int offset,
        final int len) throws IOException {
        int max = -1;
        if (this.bytesread < this.length) {
            if (offset > this.length && len > this.length) {
                max = this.input.read(buf, this.length, this.length);
            } else if (len < this.length) {
                max = this.input.read(buf, offset, len);
            } else if (len > this.length) {
                max = this.input.read(buf, offset, this.length);
            } else {
                max = this.input.read(buf, offset, len);
            }
        }
        if (max != -1) {
            this.bytesread += max;
        }
        return max;
    }

    @Override
    public long skip(final long num) throws IOException {
        this.bytesread += num;
        return this.input.skip(num);
    }

    @Override
    public int available() throws IOException {
        return this.input.available();
    }

    @Override
    public void close() throws IOException {
        this.input.close();
    }

    @Override
    public void mark(final int limit) {
        this.input.mark(limit);
    }

    @Override
    public void reset() throws IOException {
        this.input.reset();
    }

    @Override
    public boolean markSupported() {
        return this.input.markSupported();
    }

}
