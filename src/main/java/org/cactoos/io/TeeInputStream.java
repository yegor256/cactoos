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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stream that copies input to output.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class TeeInputStream extends InputStream {

    /**
     * Input.
     */
    private final InputStream input;

    /**
     * Output.
     */
    private final OutputStream output;

    /**
     * Ctor.
     * @param src Source of data
     * @param tgt Destination of data
     */
    public TeeInputStream(final InputStream src, final OutputStream tgt) {
        super();
        this.input = src;
        this.output = tgt;
    }

    @Override
    public int read() throws IOException {
        final int data = this.input.read();
        if (data >= 0) {
            this.output.write(data);
        }
        return data;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.read(buf, 0, buf.length);
    }

    @Override
    public int read(final byte[] buf, final int offset,
        final int len) throws IOException {
        final int max = this.input.read(buf, offset, len);
        if (max > 0) {
            this.output.write(buf, offset, max);
        }
        return max;
    }

    @Override
    public long skip(final long num) throws IOException {
        return this.input.skip(num);
    }

    @Override
    public int available() throws IOException {
        return this.input.available();
    }

    @Override
    public void close() throws IOException {
        this.input.close();
        this.output.flush();
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
