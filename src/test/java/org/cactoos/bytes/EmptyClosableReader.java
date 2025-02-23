/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.Reader;
import org.cactoos.io.ReaderOf;

/**
 * Empty Closable Reader
 *
 * <p>Empty {@link Reader} that can tell you if it was explicitly closed by
 * calling {@link Reader#close()} method.</p>
 *
 * <p>This class is for internal use only. Use {@link ReaderOf} instead</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.29
 */
final class EmptyClosableReader extends Reader {
    /**
     * Closed reader.
     */
    private boolean closed;

    @Override
    public int read(final char[] cbuf, final int off, final int len) {
        return -1;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    /**
     * Ask if the {@link Reader} is closed.
     * @return True if closed, false otherwise
     */
    public boolean isClosed() {
        return this.closed;
    }
}
