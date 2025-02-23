/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Stream that copies output to output.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.16
 */
public final class TeeOutputStream extends OutputStream {

    /**
     * Output.
     */
    private final OutputStream target;

    /**
     * Copy.
     */
    private final OutputStream copy;

    /**
     * Ctor.
     * @param tgt Destination of data
     * @param cpy Copy
     */
    public TeeOutputStream(final OutputStream tgt, final OutputStream cpy) {
        super();
        this.target = tgt;
        this.copy = cpy;
    }

    @Override
    public void write(final int data) throws IOException {
        try {
            this.target.write(data);
        } finally {
            this.copy.write(data);
        }
    }

    @Override
    public void write(final byte[] buf) throws IOException {
        try {
            this.target.write(buf);
        } finally {
            this.copy.write(buf);
        }
    }

    @Override
    public void write(final byte[] buf, final int off, final int len)
        throws IOException {
        try {
            this.target.write(buf, off, len);
        } finally {
            this.copy.write(buf, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.target.flush();
        } finally {
            this.copy.flush();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.target.close();
        } finally {
            this.copy.close();
        }
    }

}
