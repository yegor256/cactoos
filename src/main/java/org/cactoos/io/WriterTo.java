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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Output;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * A {@link Writer} that encapsulates other destination for the data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 */
public final class WriterTo extends Writer {

    /**
     * The target.
     */
    private final UncheckedScalar<Writer> target;

    /**
     * Ctor.
     * @param path The path
     */
    public WriterTo(final Path path) {
        this(new OutputTo(path));
    }

    /**
     * Ctor.
     * @param file The file
     */
    public WriterTo(final File file) {
        this(new OutputTo(file));
    }

    /**
     * Ctor.
     * @param stream The outpup
     */
    public WriterTo(final OutputStream stream) {
        this(new OutputTo(stream));
    }

    /**
     * Ctor.
     * @param output The input
     */
    public WriterTo(final Output output) {
        this(output, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param output The input
     * @param charset The charset
     */
    public WriterTo(final Output output, final Charset charset) {
        this(() -> new OutputStreamWriter(output.stream(), charset));
    }

    /**
     * Ctor.
     * @param output The input
     * @param charset The charset
     */
    public WriterTo(final Output output, final CharSequence charset) {
        this(() -> new OutputStreamWriter(output.stream(), charset.toString()));
    }

    /**
     * Ctor.
     * @param output The input
     * @param encoder Charset encoder
     * @since 0.13.1
     */
    public WriterTo(final Output output, final CharsetEncoder encoder) {
        this(() -> new OutputStreamWriter(output.stream(), encoder));
    }

    /**
     * Ctor.
     * @param tgt Target
     */
    private WriterTo(final Scalar<Writer> tgt) {
        super();
        this.target = new UncheckedScalar<>(new StickyScalar<>(tgt));
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len)
        throws IOException {
        this.target.value().write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.target.value().flush();
    }

    @Override
    public void close() throws IOException {
        this.target.value().close();
    }

}
