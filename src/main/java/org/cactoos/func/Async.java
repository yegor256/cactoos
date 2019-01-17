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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func that runs in the background.
 *
 * <p>If you want your piece of code to be executed in the background,
 * use {@link Async} as following:</p>
 *
 * <pre> int length = new AsyncFunc(
 *   input -&gt; input.length()
 * ).apply("Hello, world!").get();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.10
 * @todo #861:30min Avoid usage of null value in ctor(Proc, ExecutorService),
 *  ctor(Proc, ThreadFactory) and ctor(Proc) which is against design
 *  principles.
 *  Perhaps with a creation of AsyncProc or removal of this functionality?
 *  Please take a look on #551 and #843 for more details.
 */
public final class Async<X, Y> implements Func<X, Future<Y>>, Proc<X> {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * The executor service.
     */
    private final ExecutorService executor;

    /**
     * Ctor.
     * @param proc The proc
     */
    public Async(final Proc<X> proc) {
        this(new FuncOf<>(proc, null));
    }

    /**
     * Ctor.
     * @param fnc The func
     */
    public Async(final Func<X, Y> fnc) {
        this(fnc, Executors.defaultThreadFactory());
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param fct Factory
     */
    public Async(final Proc<X> proc, final ThreadFactory fct) {
        this(new FuncOf<>(proc, null), fct);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fct Factory
     */
    public Async(final Func<X, Y> fnc, final ThreadFactory fct) {
        this(fnc, Executors.newSingleThreadExecutor(fct));
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param exec Executor Service
     * @since 0.17
     */
    public Async(final Proc<X> proc, final ExecutorService exec) {
        this(new FuncOf<>(proc, null), exec);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param exec Executor Service
     */
    public Async(final Func<X, Y> fnc, final ExecutorService exec) {
        this.func = fnc;
        this.executor = exec;
    }

    @Override
    public Future<Y> apply(final X input) {
        return this.executor.submit(
            () -> this.func.apply(input)
        );
    }

    @Override
    public void exec(final X input) {
        this.apply(input);
    }
}
