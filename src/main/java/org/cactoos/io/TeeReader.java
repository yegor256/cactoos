/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Input to Output copying reader.
 *
 * <p>There is no thread-safety guarantee.
 * @since 0.13
 */
public final class TeeReader extends Reader {

    /**
     * The source.
     */
    private final Reader source;

    /**
     * The destination.
     */
    private final Writer destination;

    /**
     * Ctor.
     * @param reader The source
     * @param writer The destination
     */
    public TeeReader(final Reader reader, final Writer writer) {
        super();
        this.source = reader;
        this.destination = writer;
    }

    @Override
    public int read(final char[] cbuf, final int offset, final int length)
        throws IOException {
        final int done = this.source.read(cbuf, 0, length);
        if (done >= 0) {
            this.destination.write(cbuf);
        }
        return done;
    }

    @Override
    public void close() throws IOException {
        try {
            this.source.close();
        } finally {
            this.destination.close();
        }
    }

}
