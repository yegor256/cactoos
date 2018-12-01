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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Allows to execute the tasks concurrently and close the executor service.
 *
 * @param <X> The type of item.
 * @since 1.0.0
 */
public final class Closable<X> extends ThreadsEnvelope<X> {

    /**
     * Ctor.
     * @param tasks The tasks to be executed concurrently.
     * @param exec The executor for concurrent execution.
     * @param timeout The timeout for execution of all tasks.
     */
    public Closable(final Scalar<Duration> timeout, final Tasks<X> tasks,
        final Scalar<ExecutorService> exec) {
        this(timeout, tasks, exec, () -> Duration.ofSeconds(30));
    }

    /**
     * Ctor.
     * @param tasks The tasks to be executed concurrently.
     * @param exctor The executor for concurrent execution.
     * @param timeout The timeout for execution of all tasks.
     * @param graceful The timeout for the graceful shutdown of the executor
     *  service.
     */
    public Closable(final Scalar<Duration> timeout, final Tasks<X> tasks,
        final Scalar<ExecutorService> exctor, final Scalar<Duration> graceful) {
        super(() -> {
            final ExecutorService exc = new UncheckedScalar<>(exctor).value();
            try {
                return new Stateless<>(timeout, tasks, () -> exc).complete();
            } finally {
                try {
                    exc.shutdown();
                    final Duration gfl = new UncheckedScalar<>(graceful)
                        .value();
                    if (!exc.awaitTermination(gfl.toMillis(),
                        TimeUnit.MILLISECONDS)) {
                        exc.shutdownNow();
                    }
                } catch (final InterruptedException exp) {
                    Thread.currentThread().interrupt();
                    exc.shutdownNow();
                }
            }
        });
    }

}
