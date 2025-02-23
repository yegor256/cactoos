/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.cactoos.Input;

/**
 * Input that reads compressed data from the GZIP file format.
 *
 * @since 0.29
 */
public final class GzipInput implements Input {

    /**
     * The input.
     */
    private final Input origin;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     * @param input The input.
     */
    public GzipInput(final Input input) {
        this(input, 16 << 10);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Max length of the buffer
     */
    public GzipInput(final Input input, final int max) {
        this.origin = input;
        this.size = max;
    }

    @Override
    public InputStream stream() throws Exception {
        return new GZIPInputStream(
            this.origin.stream(),
            this.size
        );
    }
}
