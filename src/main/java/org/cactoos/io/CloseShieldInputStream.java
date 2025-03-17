/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
