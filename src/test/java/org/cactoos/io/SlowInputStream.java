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

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
     * Original stream.
     */
    private final InputStream origin;

    /**
     * Ctor.
     * @param size The size of the array to encapsulate
     */
    SlowInputStream(final int size) {
        this(new ByteArrayInputStream(new byte[size]));
    }

    /**
     * Ctor.
     * @param stream Original stream to encapsulate and make slower
     */
    SlowInputStream(final InputStream stream) {
        super();
        this.origin = stream;
    }

    @Override
    public int read() throws IOException {
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
    public int read(final byte[] buf, final int offset, final int len)
        throws IOException {
        final int result;
        if (len > 1) {
            result = this.origin.read(buf, offset, len - 1);
        } else {
            result = this.origin.read(buf, offset, len);
        }
        return result;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.read(buf, 0, buf.length);
    }

}
