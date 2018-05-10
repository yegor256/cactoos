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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import org.cactoos.Output;

/**
 * An {@link Output} that encapsulates other destination for the data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
public final class OutputTo implements Output {

    /**
     * The output.
     */
    private final Output origin;

    /**
     * Ctor.
     * @param file The file
     */
    public OutputTo(final File file) {
        this(file, true);
    }

    /**
     * Ctor.
     * @param file The file
     * @param mkdirs Should we do mkdirs beforehand?
     * @since 0.15
     */
    public OutputTo(final File file, final boolean mkdirs) {
        this(
            () -> {
                if (mkdirs) {
                    file.getParentFile().mkdirs();
                }
                return new FileOutputStream(file);
            }
        );
    }

    /**
     * Ctor.
     * @param path The path
     */
    public OutputTo(final Path path) {
        this(path, true);
    }

    /**
     * Ctor.
     * @param path The path
     * @param mkdirs Should we do mkdirs beforehand?
     * @since 0.15
     */
    public OutputTo(final Path path, final boolean mkdirs) {
        this(
            () -> {
                final File file = path.toFile();
                if (mkdirs) {
                    file.getParentFile().mkdirs();
                }
                return new FileOutputStream(file);
            }
        );
    }

    /**
     * Ctor.
     * @param writer The writer
     */
    public OutputTo(final Writer writer) {
        this(new WriterAsOutputStream(writer));
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     */
    OutputTo(final Writer wtr, final Charset charset) {
        this(new WriterAsOutputStream(wtr, charset));
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     */
    OutputTo(final Writer wtr, final CharSequence charset) {
        this(new WriterAsOutputStream(wtr, charset));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     */
    OutputTo(final Writer wtr, final Charset charset,
        final int size) {
        this(new WriterAsOutputStream(wtr, charset, size));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param size Buffer size
     * @since 0.13.3
     */
    OutputTo(final Writer wtr, final int size) {
        this(new WriterAsOutputStream(wtr, size));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     */
    OutputTo(final Writer wtr, final CharSequence charset,
        final int size) {
        this(new WriterAsOutputStream(wtr, charset, size));
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param ddr Charset decoder
     * @param size Buffer size
     */
    OutputTo(final Writer wtr, final CharsetDecoder ddr,
        final int size) {
        this(new WriterAsOutputStream(wtr, ddr, size));
    }

    /**
     * Ctor.
     * @param stream The stream
     */
    public OutputTo(final OutputStream stream) {
        this(() -> stream);
    }

    /**
     * Ctor.
     * @param output The output
     */
    private OutputTo(final Output output) {
        this.origin = output;
    }

    @Override
    public OutputStream stream() throws Exception {
        return this.origin.stream();
    }

}
