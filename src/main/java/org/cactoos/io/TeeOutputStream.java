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
import java.io.OutputStream;

/**
 * Stream that copies output to output.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.16
 */
public final class TeeOutputStream extends OutputStream {

    /**
     * Output.
     */
    private final OutputStream target;

    /**
     * Copy.
     */
    private final OutputStream copy;

    /**
     * Ctor.
     * @param tgt Destination of data
     * @param cpy Copy
     */
    public TeeOutputStream(final OutputStream tgt, final OutputStream cpy) {
        super();
        this.target = tgt;
        this.copy = cpy;
    }

    @Override
    public void write(final int data) throws IOException {
        try {
            this.target.write(data);
        } finally {
            this.copy.write(data);
        }
    }

    @Override
    public void write(final byte[] buf) throws IOException {
        try {
            this.target.write(buf);
        } finally {
            this.copy.write(buf);
        }
    }

    @Override
    public void write(final byte[] buf, final int off, final int len)
        throws IOException {
        try {
            this.target.write(buf, off, len);
        } finally {
            this.copy.write(buf, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.target.flush();
        } finally {
            this.copy.flush();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.target.close();
        } finally {
            this.copy.close();
        }
    }

}
