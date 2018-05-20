/*
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Function that gets interrupted after a certain time has passed.
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.29.3
 * @todo #861:30min Avoid usage of null value in ctor(Proc, long) which is
 *  against design principles.
 *  Perhaps in creating TimedProc?
 *  Please take a look on #551 and #843 for more details.
 */
public final class TimedFunc<X, Y> implements Func<X, Y> {

    /**
     * Origin function.
     */
    private final Func<X, Future<Y>> func;

    /**
     * Milliseconds.
     */
    private final long time;

    /**
     * Ctor.
     * @param proc Proc
     * @param milliseconds Milliseconds
     */
    public TimedFunc(final Proc<X> proc, final long milliseconds) {
        this(new FuncOf<>(proc, null), milliseconds);
    }

    /**
     * Ctor.
     * @param function Origin function
     * @param milliseconds Milliseconds
     */
    public TimedFunc(final Func<X, Y> function, final long milliseconds) {
        this(milliseconds, new AsyncFunc<>(function));
    }

    /**
     * Ctor.
     * @param async Async function
     * @param milliseconds Milliseconds
     */
    public TimedFunc(final long milliseconds, final Func<X, Future<Y>> async) {
        this.func = async;
        this.time = milliseconds;
    }

    @Override
    public Y apply(final X input) throws Exception {
        final Future<Y> future = this.func.apply(input);
        try {
            return future.get(this.time, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException | ExecutionException
            | TimeoutException exp) {
            future.cancel(true);
            throw exp;
        }
    }
}
