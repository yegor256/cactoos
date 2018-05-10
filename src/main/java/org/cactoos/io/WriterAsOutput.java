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

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import org.cactoos.Output;

/**
 * Writer as {@link Output}.
 *
 * <p>This class is for internal use only. Use {@link OutputTo} instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.13
 */
final class WriterAsOutput implements Output {

    /**
     * The writer.
     */
    private final Writer writer;

    /**
     * The charset decoder.
     */
    private final CharsetDecoder decoder;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param wtr Writer
     */
    WriterAsOutput(final Writer wtr) {
        this(wtr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param cset Charset
     */
    WriterAsOutput(final Writer wtr, final Charset cset) {
        // @checkstyle MagicNumber (1 line)
        this(wtr, cset, 16 << 10);
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    WriterAsOutput(final Writer wtr, final Charset cset, final int max) {
        this(wtr, cset.newDecoder(), max);
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param ddr Decoder
     * @param max Buffer size
     */
    WriterAsOutput(final Writer wtr, final CharsetDecoder ddr, final int max) {
        this.writer = wtr;
        this.decoder = ddr;
        this.size = max;
    }

    @Override
    public OutputStream stream() {
        return new WriterAsOutputStream(this.writer, this.decoder, this.size);
    }

}
