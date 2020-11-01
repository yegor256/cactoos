/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;

/**
 * Allows to execute the tasks concurrently.
 *
 * @param <T> The type of task result item.
 * @since 1.0.0
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
     */
    public Threads(final ExecutorService exc, final Iterable<Scalar<T>> tasks) {
        this(input -> exc.invokeAll(new ListOf<>(input)), tasks);
    }

    /**
     * Ctor.
     * @param threads The quantity of threads which will be used within the
     *  {@link ExecutorService}.
     * @param tasks The tasks to be executed concurrently.
     * @see Executors#newFixedThreadPool(int)
     */
    @SafeVarargs
    public Threads(final int threads, final Scalar<T>... tasks) {
        this(threads, new IterableOf<>(tasks));
    }

    /**
     * Ctor.
     * @param threads The quantity of threads which will be used within the
     *  {@link ExecutorService}.
     * @param tasks The tasks to be executed concurrently.
     * @checkstyle IndentationCheck (20 lines)
     */
    public Threads(final int threads, final Iterable<Scalar<T>> tasks) {
        this(
            todo -> {
                final ExecutorService executor = Executors.newFixedThreadPool(
                    threads
                );
                try {
                    return executor.invokeAll(new ListOf<>(todo));
                } finally {
                    executor.shutdown();
                }
            },
            tasks
        );
    }

    /**
     * Ctor.
     * @param fnc The function to map each task into {@link Future}.
     * @param tasks The tasks to be executed concurrently.
     */
    private Threads(
        final Func<Iterable<Callable<T>>, Iterable<Future<T>>> fnc,
        final Iterable<Scalar<T>> tasks
    ) {
        super(
            () -> new Mapped<>(
                Future::get,
                new UncheckedFunc<>(fnc).apply(
                    new Mapped<>(
                        task -> () -> task.value(),
                        tasks
                    )
                )
            ).iterator()
        );
    }
}
