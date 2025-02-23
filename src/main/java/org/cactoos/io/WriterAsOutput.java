/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
