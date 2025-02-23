/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Decorator of {@link OutputStream} to prevent it
 * to be closed.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class CloseShieldOutputStream extends OutputStream {

    /**
     * Inner {@link OutputStream}.
     */
    private final AtomicReference<OutputStream> inner;

    /**
     * Ctor.
     * @param origin Origin
     */
    public CloseShieldOutputStream(final OutputStream origin) {
        super();
        this.inner = new AtomicReference<>(origin);
    }

    @Override
    public void write(final int data) throws IOException {
        this.inner.get().write(data);
    }

    @Override
    public void write(final byte[] buf) throws IOException {
        this.inner.get().write(buf);
    }

    @Override
    public void write(final byte[] buf, final int off, final int len)
        throws IOException {
        this.inner.get().write(buf, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.inner.get().flush();
    }

    @Override
    public void close() {
        this.inner.set(new DeadOutputStream());
    }

}
