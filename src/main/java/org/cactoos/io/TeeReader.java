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
import java.io.Writer;

/**
 * Input to Output copying reader.
 *
 * <p>There is no thread-safety guarantee.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.13
 */
public final class TeeReader extends Reader {

    /**
     * The source.
     */
    private final Reader source;

    /**
     * The destination.
     */
    private final Writer destination;

    /**
     * Ctor.
     * @param reader The source
     * @param writer The destination
     */
    public TeeReader(final Reader reader, final Writer writer) {
        super();
        this.source = reader;
        this.destination = writer;
    }

    @Override
    public int read(final char[] cbuf, final int offset, final int length)
        throws IOException {
        final int done = this.source.read(cbuf, 0, length);
        if (done >= 0) {
            this.destination.write(cbuf);
        }
        return done;
    }

    @Override
    public void close() throws IOException {
        try {
            this.source.close();
        } finally {
            this.destination.close();
        }
    }

}
