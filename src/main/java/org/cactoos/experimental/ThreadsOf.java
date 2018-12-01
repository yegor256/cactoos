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
package org.cactoos.experimental;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;

/**
 * Allows to execute the iterable of {@link Scalar} concurrently.
 *
 * @param <X> The type of item.
 *
 * @since 1.0.0
 */
public class ThreadsOf<X> extends ThreadsEnvelope<X> {

    /**
     * Ctor.
     * @param timeout The timeout for execution of all tasks.
     * @param tasks The tasks to be executed concurrently.
     */
    @SafeVarargs
    public ThreadsOf(final Scalar<Duration> timeout, final Scalar<X>... tasks) {
        this(timeout, new TasksOf<>(tasks));
    }

    /**
     * Ctor.
     * @param timeout The timeout for execution of all tasks.
     * @param tsk The tasks to be executed concurrently.
     */
    @SafeVarargs
    public ThreadsOf(final Scalar<Duration> timeout, final Callable<X>... tsk) {
        this(timeout, new TasksOf<>(tsk));
    }

    /**
     * Ctor.
     * @param timeout The timeout for execution of all tasks.
     * @param tasks The tasks to be executed concurrently.
     */
    public ThreadsOf(final Scalar<Duration> timeout, final Tasks<X> tasks) {
        super(new Closable<>(
            timeout, tasks, () -> Executors.newFixedThreadPool(5)
        ));
    }

    /**
     * Ctor.
     * @param timeout The timeout for execution of all tasks.
     * @param tasks The tasks to be executed concurrently.
     * @param exec The executor for concurrent execution.
     */
    public ThreadsOf(final Scalar<Duration> timeout, final Tasks<X> tasks,
        final Scalar<ExecutorService> exec) {
        super(new Stateless<>(timeout, tasks, new StickyScalar<>(exec)));
    }

}
