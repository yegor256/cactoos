/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream that only shows the first N bytes of the original stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.31
 */
public final class HeadInputStream extends InputStream {

    /**
     * Original input stream.
     */
    private final InputStream origin;

    /**
     * A number of bytes that can be read from the beginning.
     */
    private final long length;

    /**
     * Current number or read bytes.
     */
    private long processed;

    /**
     * Ctor.
     * @param orig The original input stream.
     * @param len A number of bytes that can be read from the beginning.
     */
    public HeadInputStream(final InputStream orig, final int len) {
        super();
        this.origin = orig;
        this.length = (long) len;
    }

    @Override
    public int read() throws IOException {
        final int adjusted;
        if (this.processed >= this.length) {
            adjusted = -1;
        } else {
            this.processed += 1L;
            adjusted = this.origin.read();
        }
        return adjusted;
    }

    @Override
    public long skip(final long skip) throws IOException {
        final long adjusted;
        if (this.processed + skip > this.length) {
            adjusted = this.length - this.processed;
        } else {
            adjusted = skip;
        }
        final long skipped = this.origin.skip(adjusted);
        this.processed += skipped;
        return skipped;
    }

    @Override
    public void reset() throws IOException {
        this.processed = 0L;
        this.origin.reset();
    }

    @Override
    public int available() throws IOException {
        final int available = this.origin.available();
        final int adjusted;
        if (this.processed + (long) available > this.length) {
            adjusted = (int) (this.length - this.processed);
        } else {
            adjusted = available;
        }
        return adjusted;
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
    }

    @Override
    public boolean markSupported() {
        return this.origin.markSupported();
    }

    @Override
    public void mark(final int readlimit) {
        this.origin.mark(readlimit);
    }
}
