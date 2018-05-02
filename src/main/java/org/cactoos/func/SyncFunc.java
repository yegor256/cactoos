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

import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func that is thread-safe.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.4
 */
public final class SyncFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> func;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param runnable Func original
     * @since 0.12
     */
    public SyncFunc(final Runnable runnable) {
        this(new FuncOf<>(runnable));
    }

    /**
     * Ctor.
     * @param callable Func original
     * @since 0.12
     */
    public SyncFunc(final Callable<Y> callable) {
        this(new FuncOf<>(callable));
    }

    /**
     * Ctor.
     * @param proc Func original
     * @since 0.12
     */
    public SyncFunc(final Proc<X> proc) {
        this(new FuncOf<>(proc));
    }

    /**
     * Ctor.
     * @param fnc Func original
     */
    public SyncFunc(final Func<X, Y> fnc) {
        this(fnc, fnc);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param lck Sync lock
     */
    public SyncFunc(final Func<X, Y> fnc, final Object lck) {
        this.func = fnc;
        this.lock = lck;
    }

    @Override
    public Y apply(final X input) throws Exception {
        synchronized (this.lock) {
            return this.func.apply(input);
        }
    }
}
