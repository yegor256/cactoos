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

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.collection.Mapped;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;

/**
 * Allows to execute the tasks concurrently.
 *
 * @param <T> The type of task result item.
 * @since 1.0.0
 * @todo #972:30min j.u.c.ExecutorService#invokeAll(java.util.Collection) should
 *  be invoked with timeout. User should have the opportunity to pass the
 *  timeout argument. We need a new decorator which supports the timeout.
 * @todo #972:30min Add a new implementation which receive the tasks and able:
 *  - create a new thread pool with predefined/specified size;
 *  - execute tasks concurrently;
 *  - shutdown the thread pool.
 */
public final class Threads<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param exc The executor.
     * @param tasks The tasks to be executed concurrently.
     */
    @SafeVarargs
    public Threads(final ExecutorService exc, final Scalar<T>... tasks) {
        this(exc, new IterableOf<>(tasks));
    }

    /**
     * Ctor.
     * @param exc The executor.
     * @param tasks The tasks to be executed concurrently.
     * @checkstyle IllegalCatchCheck (20 lines)
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Threads(final ExecutorService exc, final Iterable<Scalar<T>> tasks) {
        super(() -> {
            try {
                return new Mapped<>(
                    Future::get,
                    exc.invokeAll(
                        new Mapped<>(
                            (Func<Scalar<T>, Callable<T>>) task -> task::value,
                            tasks
                        )
                    )
                );
            } catch (final Exception exp) {
                Thread.currentThread().interrupt();
                throw new CompletionException(exp);
            }
        });
    }
}
