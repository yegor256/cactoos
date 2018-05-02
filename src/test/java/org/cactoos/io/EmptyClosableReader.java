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
import java.io.Reader;

/**
 * Empty Closable Reader
 *
 * <p>Empty {@link Reader} that can tell you if it was explicitly closed by
 * calling {@link Reader#close()} method.</p>
 *
 * <p>This class is for internal use only. Use {@link ReaderOf} instead</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Alexey Kutepov (reximkut@gmail.com)
 * @version $Id$
 * @since 0.29
 */
final class EmptyClosableReader extends Reader {
    /**
     * Closed reader.
     */
    private boolean closed;

    @Override
    public int read(final char[] cbuf, final int off, final int len)
        throws IOException {
        return -1;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    /**
     * Ask if the {@link Reader} is closed.
     * @return True if closed, false otherwise
     */
    public boolean isClosed() {
        return this.closed;
    }
}
