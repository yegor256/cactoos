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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.collection.Mapped;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Execute tasks concurrently without saving state.
 *
 * @param <X> Type of item.
 * @since 1.0.0
 */
public final class Stateless<X> implements Threads<X> {

    /**
     * The tasks to be executed concurrently.
     */
    private final Tasks<X> tasks;

    /**
     * The timeout for execution of all tasks.
     */
    private final Scalar<Duration> timeout;

    /**
     * The executor for concurrent execution.
     */
    private final UncheckedScalar<ExecutorService> exec;

    /**
     * Ctor.
     * @param timeout The timeout for execution of all tasks.
     * @param tasks The tasks to be executed concurrently.
     * @param exec The executor for concurrent execution.
     */
    public Stateless(final Scalar<Duration> timeout, final Tasks<X> tasks,
        final Scalar<ExecutorService> exec) {
        this.timeout = timeout;
        this.tasks = tasks;
        this.exec = new UncheckedScalar<>(exec);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Iterable<X> complete() throws ConcurrentExecutionException {
        // @checkstyle IllegalCatchCheck (50 lines)
        try {
            return new Mapped<Future<X>, X>(
                Future::get,
                this.exec.value().invokeAll(
                    new Mapped<>(t -> (Callable<X>) t::value, this.tasks),
                    this.timeout.value().toMillis(),
                    TimeUnit.MILLISECONDS
                )
            );
        } catch (final InterruptedException exp) {
            Thread.currentThread().interrupt();
            throw new ConcurrentExecutionException(exp);
        } catch (final Exception exp) {
            throw new ConcurrentExecutionException(exp);
        }
    }
}
