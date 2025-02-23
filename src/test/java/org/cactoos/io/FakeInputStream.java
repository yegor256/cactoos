/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Fake {@link InputStream} with ability to check if
 * it is closed.
 * @since 1.0.0
 */
final class FakeInputStream extends InputStream {

    /**
     * If {@link InputStream} is closed.
     */
    private final AtomicBoolean closed;

    /**
     * Ctor.
     */
    FakeInputStream() {
        this.closed = new AtomicBoolean(false);
    }

    @Override
    public int read() {
        return -1;
    }

    @Override
    public void close() {
        this.closed.set(true);
    }

    /**
     * If stream is closed.
     * @return Closed or not
     */
    public boolean isClosed() {
        return this.closed.get();
    }
}
