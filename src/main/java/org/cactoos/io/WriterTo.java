/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * A {@link Writer} that encapsulates other destination for the data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.13
 */
public final class WriterTo extends Writer {

    /**
     * The target.
     */
    private final Unchecked<Writer> target;

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
     * @param stream The output
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
        this.target = new Unchecked<>(
            new Sticky<>(tgt)
        );
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
