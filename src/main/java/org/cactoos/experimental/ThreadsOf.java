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

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.collection.Mapped;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Allows to execute the tasks concurrently.
 *
 * @param <X> The type of item.
 * @since 1.0.0
 * @todo #972:30min Threads shouldn't extend the Iterable as we need to throw
 *  the exception in case of unexpected issues. For example the signature of
 *  such method might be the following:
 *  <pre>{@code
 *  public Iterable<X> complete() throws ConcurrentExecutionException;
 *  }</pre>.
 *  It will allows to avoid the throwing of CompletionException which is runtime
 *  exception.
 * @todo #972:30min j.u.c.ExecutorService#invokeAll(java.util.Collection) should
 *  be invoked with timeout. User should have the opportunity to pass the
 *  timeout argument. We need a new decorator which supports the timeout
 */
public final class ThreadsOf<X> implements Threads<X> {

    /**
     * The tasks to be executed concurrently.
     */
    private final Iterable<Scalar<X>> tasks;

    /**
     * The executor.
     */
    private final Scalar<ExecutorService> exc;

    /**
     * Ctor.
     * @param exc The executor.
     *  Please make attention how the executor service was passed to scalar.
     *  It's better to wrap in {@link StickyScalar}.
     * @param tasks The tasks to be executed concurrently.
     */
    @SafeVarargs
    public ThreadsOf(
        final Scalar<ExecutorService> exc, final Scalar<X>... tasks
    ) {
        this(exc, new IterableOf<>(tasks));
    }

    /**
     * Ctor.
     * @param exc The executor.
     *  Please make attention how the executor service was wrapped into scalar.
     *  It's better to wrap it into {@link StickyScalar}.
     * @param tasks The tasks to be executed concurrently.
     */
    public ThreadsOf(
        final Scalar<ExecutorService> exc, final Iterable<Scalar<X>> tasks
    ) {
        this.exc = exc;
        this.tasks = tasks;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public Iterator<X> iterator() {
        // @checkstyle IllegalCatchCheck (50 lines)
        try {
            return new Mapped<>(
                Future::get,
                this.exc.value().invokeAll(
                    new Mapped<>(
                        (Func<Scalar<X>, Callable<X>>) task -> task::value,
                        this.tasks
                    )
                )
            ).iterator();
        } catch (final Exception exp) {
            Thread.currentThread().interrupt();
            throw new CompletionException(exp);
        }
    }
}
