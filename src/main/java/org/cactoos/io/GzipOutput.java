/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.cactoos.Output;

/**
 * Output that writes compressed data in the GZIP file format.
 *
 * @since 0.29
 */
public final class GzipOutput implements Output {

    /**
     * The output.
     */
    private final Output origin;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param output The output
     */
    public GzipOutput(final Output output) {
        this(output, 16 << 10);
    }

    /**
     * Ctor.
     * @param output The output
     * @param max Max length of the buffer
     */
    public GzipOutput(final Output output, final int max) {
        this.origin = output;
        this.size = max;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new GZIPOutputStream(
            this.origin.stream(),
            this.size
        );
    }
}
