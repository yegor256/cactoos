/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Fake {@link OutptStream} with ability to check if
 * it is closed.
 * @since 1.0.0
 */
final class FakeOutputStream extends OutputStream {

    /**
     * If {@link OutputStream} is closed.
     */
    private final AtomicBoolean closed;

    /**
     * Ctor.
     */
    FakeOutputStream() {
        this.closed = new AtomicBoolean(false);
    }

    @Override
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    // @checkstyle ParameterNameCheck (1 lines)
    public void write(final int b) {
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
