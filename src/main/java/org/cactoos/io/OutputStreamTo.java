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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Output;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * An {@link OutputStream} that encapsulates other destination for the data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 */
public final class OutputStreamTo extends OutputStream {

    /**
     * The target.
     */
    private final UncheckedScalar<OutputStream> target;

    /**
     * Ctor.
     * @param path The path
     */
    public OutputStreamTo(final Path path) {
        this(new OutputTo(path));
    }

    /**
     * Ctor.
     * @param file The file
     */
    public OutputStreamTo(final File file) {
        this(new OutputTo(file));
    }

    /**
     * Ctor.
     * @param wtr The writer
     */
    public OutputStreamTo(final Writer wtr) {
        this(wtr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     * @since 0.13.1
     */
    public OutputStreamTo(final Writer wtr, final Charset charset) {
        this(new OutputTo(wtr, charset));
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     * @since 0.13.1
     */
    public OutputStreamTo(final Writer wtr, final CharSequence charset) {
        this(new OutputTo(wtr, charset));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     * @since 0.13.1
     */
    public OutputStreamTo(final Writer wtr, final Charset charset,
        final int size) {
        this(new OutputTo(wtr, charset, size));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     * @since 0.13.1
     */
    public OutputStreamTo(final Writer wtr, final CharSequence charset,
        final int size) {
        this(new OutputTo(wtr, charset, size));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param ddr Charset decoder
     * @param size Buffer size
     * @since 0.13.1
     */
    public OutputStreamTo(final Writer wtr, final CharsetDecoder ddr,
        final int size) {
        this(new OutputTo(wtr, ddr, size));
    }

    /**
     * Ctor.
     * @param output The input
     */
    public OutputStreamTo(final Output output) {
        this((Scalar<OutputStream>) output::stream);
    }

    /**
     * Ctor.
     * @param tgt Target
     */
    private OutputStreamTo(final Scalar<OutputStream> tgt) {
        super();
        this.target = new UncheckedScalar<>(
            new StickyScalar<>(tgt)
        );
    }

    @Override
    public void write(final int data) throws IOException {
        this.target.value().write(data);
    }

    @Override
    public void write(final byte[] buffer) throws IOException {
        this.target.value().write(buffer);
    }

    @Override
    public void write(final byte[] buffer, final int offset,
        final int length) throws IOException {
        this.target.value().write(buffer, offset, length);
    }

    @Override
    public void close() throws IOException {
        this.target.value().close();
    }

    @Override
    public void flush() throws IOException {
        this.target.value().flush();
    }

}
