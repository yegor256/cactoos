/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
    public void write(final int b) throws IOException {
    }

    @Override
    public void close() throws IOException {
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
