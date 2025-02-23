/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stream that copies input to output.
 * <b>WARNING:</b>
 * This class closes {@link TeeInputStream#output}
 * after {@link TeeInputStream#close()}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class TeeInputStream extends InputStream {

    /**
     * Input.
     */
    private final InputStream input;

    /**
     * Output.
     */
    private final OutputStream output;

    /**
     * Ctor.
     * @param src Source of data
     * @param tgt Destination of data
     */
    public TeeInputStream(final InputStream src, final OutputStream tgt) {
        super();
        this.input = src;
        this.output = tgt;
    }

    @Override
    public int read() throws IOException {
        final int data = this.input.read();
        if (data >= 0) {
            this.output.write(data);
        }
        return data;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.read(buf, 0, buf.length);
    }

    @Override
    public int read(final byte[] buf, final int offset,
        final int len) throws IOException {
        final int max = this.input.read(buf, offset, len);
        if (max > 0) {
            this.output.write(buf, offset, max);
        }
        return max;
    }

    @Override
    public long skip(final long num) throws IOException {
        return this.input.skip(num);
    }

    @Override
    public int available() throws IOException {
        return this.input.available();
    }

    @Override
    public void close() throws IOException {
        this.input.close();
        this.output.close();
    }

    @Override
    public void mark(final int limit) {
        this.input.mark(limit);
    }

    @Override
    public void reset() throws IOException {
        this.input.reset();
    }

    @Override
    public boolean markSupported() {
        return this.input.markSupported();
    }

}
