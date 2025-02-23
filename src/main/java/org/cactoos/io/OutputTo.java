/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
    @SuppressWarnings("PMD.AvoidFileStream")
    public OutputTo(final File file, final boolean mkdirs) {
        this(
            () -> {
                if (mkdirs) {
                    file.getAbsoluteFile().getParentFile().mkdirs();
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
    @SuppressWarnings("PMD.AvoidFileStream")
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
