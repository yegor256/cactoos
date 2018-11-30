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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.collection.Mapped;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Allows to execute the iterable of {@link Scalar} concurrently.
 *
 * @param <X> The type of item.
 *
 * @since 1.0.0
 */
public class Threads<X> implements Iterable<X> {

    /**
     * The results of concurrent execution.
     */
    private final Iterable<X> rslts;

    /**
     * Ctor.
     * @param ttimeout The timeout per task.
     * @param tasks The tasks to be executed concurrently.
     */
    @SafeVarargs
    public Threads(final Scalar<Duration> ttimeout, final Scalar<X>... tasks) {
        this(ttimeout, new ListOf<>(tasks));
    }

    /**
     * Ctor.
     * @param ttimeout The timeout per task.
     * @param tasks The tasks to be executed concurrently.
     */
    public Threads(final Scalar<Duration> ttimeout,
        final Collection<Scalar<X>> tasks) {
        this(tasks, ttimeout, () -> Executors.newFixedThreadPool(5));
    }

    /**
     * Ctor.
     * @param tasks The tasks to be executed concurrently.
     * @param ttimeout The timeout per task.
     * @param extr The executor for concurrent execution.
     */
    public Threads(final Collection<Scalar<X>> tasks,
        final Scalar<Duration> ttimeout,
        final Scalar<ExecutorService> extr) {
        this(tasks, ttimeout, todo -> {
                final ExecutorService exctor = new StickyScalar<>(extr).value();
                final Collection<Future<X>> out = new ArrayList<>(todo.size());
                for (final Scalar<X> task : todo) {
                    out.add(exctor.submit(task::value));
                }
                return out;
            }
        );
    }

    /**
     * Ctor.
     * @param tasks The tasks to be executed concurrently.
     * @param ttimeout The timeout per task.
     * @param fnc The function to execute tasks concurrently.
     * @todo #962:30m Move the Future.get to separate function - Timeouted.
     */
    public Threads(final Collection<Scalar<X>> tasks,
        final Scalar<Duration> ttimeout,
        final Func<Collection<Scalar<X>>, Collection<Future<X>>> fnc) {
        this(
            new Mapped<Future<X>, X>(
                f -> f.get(ttimeout.value().toMillis(), TimeUnit.MILLISECONDS),
                new UncheckedFunc<>(fnc).apply(tasks)
            )
        );
    }

    /**
     * Ctor.
     * @param rslts The results of concurrent execution.
     */
    private Threads(final Iterable<X> rslts) {
        this.rslts = rslts;
    }

    @Override
    public Iterator<X> iterator() {
        return this.rslts.iterator();
    }
}
