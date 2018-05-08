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

package org.cactoos.func;

import org.cactoos.Proc;

/**
 * Proc that is thread-safe.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @since 0.32
 */
public final class SyncProc<X> implements Proc<X> {
    /**
     * Original proc.
     */
    private final Proc<X> origin;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param runnable Runnable original
     */
    public SyncProc(final Runnable runnable) {
        this(new ProcOf<>(runnable));
    }

    /**
     * Ctor.
     * @param origin Proc original
     */
    public SyncProc(final Proc<X> origin) {
        this.origin = origin;
        this.lock = new Object();
    }

    @Override
    public void exec(final X input) throws Exception {
        synchronized (this.lock) {
            this.origin.exec(input);
        }
    }
}
