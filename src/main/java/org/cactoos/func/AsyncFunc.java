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
 * use {@link AsyncFunc} as following:</p>
 *
 * <pre> int length = new AsyncFunc(
 *   input -&gt; input.length()
 * ).apply("Hello, world!").get();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.10
 */
public final class AsyncFunc<X, Y> implements Func<X, Future<Y>>, Proc<X> {

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
    public AsyncFunc(final Proc<X> proc) {
        this(new FuncOf<>(proc));
    }

    /**
     * Ctor.
     * @param fnc The func
     */
    public AsyncFunc(final Func<X, Y> fnc) {
        this(fnc, Executors.defaultThreadFactory());
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param fct Factory
     */
    public AsyncFunc(final Proc<X> proc, final ThreadFactory fct) {
        this(new FuncOf<>(proc), fct);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fct Factory
     */
    public AsyncFunc(final Func<X, Y> fnc, final ThreadFactory fct) {
        this(fnc, Executors.newSingleThreadExecutor(fct));
    }

    /**
     * Ctor.
     * @param proc The proc
     * @param exec Executor Service
     * @since 0.17
     */
    public AsyncFunc(final Proc<X> proc, final ExecutorService exec) {
        this(new FuncOf<>(proc), exec);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param exec Executor Service
     */
    public AsyncFunc(final Func<X, Y> fnc, final ExecutorService exec) {
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
