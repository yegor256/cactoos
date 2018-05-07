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

/**
 * Input stream that only shows the first N bytes of the original stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.31
 */
public final class HeadInputStream extends InputStream {

    /**
     * Original input stream.
     */
    private final InputStream origin;

    /**
     * A number of bytes that can be read from the beginning.
     */
    private final long length;

    /**
     * Current number or read bytes.
     */
    private long processed;

    /**
     * Ctor.
     * @param orig The original input stream.
     * @param len A number of bytes that can be read from the beginning.
     */
    public HeadInputStream(final InputStream orig, final int len) {
        super();
        this.origin = orig;
        this.length = len;
    }

    @Override
    public int read() throws IOException {
        final int adjusted;
        if (this.processed >= this.length) {
            adjusted = -1;
        } else {
            this.processed = this.processed + 1;
            adjusted = this.origin.read();
        }
        return adjusted;
    }

    @Override
    public long skip(final long skip) throws IOException {
        final long adjusted;
        if (this.processed + skip > this.length) {
            adjusted = this.length - this.processed;
        } else {
            adjusted = skip;
        }
        final long skipped = this.origin.skip(adjusted);
        this.processed = this.processed + skipped;
        return skipped;
    }

    @Override
    public void reset() throws IOException {
        this.processed = 0L;
        this.origin.reset();
    }

    @Override
    public int available() throws IOException {
        final int available = this.origin.available();
        final int adjusted;
        if (this.processed + available > this.length) {
            adjusted = (int) (this.length - this.processed);
        } else {
            adjusted = available;
        }
        return adjusted;
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
    }

    @Override
    public boolean markSupported() {
        return this.origin.markSupported();
    }

    @Override
    public void mark(final int readlimit) {
        this.origin.mark(readlimit);
    }
}
