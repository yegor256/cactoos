/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.Input;

/**
 * Thread-safe {@link Input}.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.18
 */
public final class SyncInput implements Input {

    /**
     * The input.
     */
    private final Input origin;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param input The input
     */
    public SyncInput(final Input input) {
        this(input, input);
    }

    /**
     * Ctor.
     * @param input The input
     * @param lck The lock object
     */
    public SyncInput(final Input input, final Object lck) {
        this.origin = input;
        this.lock = lck;
    }

    @Override
    public InputStream stream() throws IOException {
        synchronized (this.lock) {
            return this.origin.stream();
        }
    }
}
