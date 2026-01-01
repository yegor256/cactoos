/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Decorator of {@link InputStream} to prevent it
 * to be closed.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class CloseShieldInputStream extends InputStream {

    /**
     * Inner {@link InputStream}.
     */
    private final AtomicReference<InputStream> inner;

    /**
     * Ctor.
     * @param origin Origin
     */
    public CloseShieldInputStream(final InputStream origin) {
        super();
        this.inner = new AtomicReference<>(origin);
    }

    @Override
    public int read() throws IOException {
        return this.inner.get().read();
    }

    @Override
    public int read(final byte[] buffer) throws IOException {
        return this.inner.get().read(buffer);
    }

    @Override
    public int read(final byte[] buffer, final int offset,
        final int length) throws IOException {
        return this.inner.get().read(buffer, offset, length);
    }

    @Override
    public void close() {
        this.inner.set(new DeadInputStream());
    }

    @Override
    public long skip(final long num) throws IOException {
        return this.inner.get().skip(num);
    }

    @Override
    public int available() throws IOException {
        return this.inner.get().available();
    }

    @Override
    public void mark(final int limit) {
        this.inner.get().mark(limit);
    }

    @Override
    public void reset() throws IOException {
        this.inner.get().reset();
    }

    @Override
    public boolean markSupported() {
        return this.inner.get().markSupported();
    }

}
