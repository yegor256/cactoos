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

import java.io.InputStream;

/**
 * InputStream that returns content in small portions.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.12
 */
final class SlowInputStream extends InputStream {

    /**
     * How much is left.
     */
    private int left;

    /**
     * Byte to return.
     */
    private final byte data;

    /**
     * Ctor.
     * @param size Total amount of bytes to return
     */
    SlowInputStream(final int size) {
        this(size, 'A');
    }

    /**
     * Ctor.
     * @param size Total amount of bytes to return
     * @param dta Data to return
     */
    SlowInputStream(final int size, final char dta) {
        this(size, (byte) dta);
    }

    /**
     * Ctor.
     * @param size Total amount of bytes to return
     * @param dta Data to return
     */
    SlowInputStream(final int size, final byte dta) {
        super();
        this.left = size;
        this.data = dta;
    }

    @Override
    public int read() {
        final byte[] buf = new byte[1];
        final int result;
        if (this.read(buf) < 0) {
            result = -1;
        } else {
            result = buf[0];
        }
        return result;
    }

    @Override
    public int read(final byte[] buf, final int offset, final int len) {
        int idx = 0;
        for (; idx < buf.length - 1 && idx < this.left; ++idx) {
            buf[idx] = this.data;
        }
        this.left -= idx;
        int result = idx;
        if (idx == 0) {
            result = -1;
        }
        return result;
    }

    @Override
    public int read(final byte[] buf) {
        return this.read(buf, 0, buf.length);
    }

}
