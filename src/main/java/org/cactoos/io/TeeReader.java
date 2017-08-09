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
import java.io.Writer;
import java.nio.charset.Charset;
import org.cactoos.Input;
import org.cactoos.Output;

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
    private final Reader reader;

    /**
     * The destination.
     */
    private final Writer writer;

    // @checkstyle ParameterNumberCheck (8 line)
    /**
     * Ctor.
     * @param input The source
     * @param incharset The source charset
     * @param out The destination
     * @param outcharset The destination charset
     */
    TeeReader(final Input input, final Charset incharset,
        final Output out, final Charset outcharset) {
        super();
        this.reader = new ReaderOf(input, incharset);
        this.writer = new WriterTo(out, outcharset);
    }

    @Override
    public int read(final char[] cbuf, final int offset, final int length)
        throws IOException {
        final int done = this.reader.read(cbuf, 0, length);
        if (done >= 0) {
            this.writer.write(cbuf);
        }
        return done;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
    }

}
