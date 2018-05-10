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
package org.cactoos.scalar;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical conjunction, in multiple threads.
 *
 * <p>The usage is same as for {@link And}</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see UncheckedScalar
 * @see IoCheckedScalar
 * @since 0.25
 */
public final class AndInThreads implements Scalar<Boolean> {

    /**
     * The service.
     */
    private final ExecutorService service;

    /**
     * The iterator.
     */
    private final Iterable<Scalar<Boolean>> iterable;

    /**
     * Shut down the service when it's done.
     */
    private final boolean shut;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndInThreads(final Proc<X> proc, final X... src) {
        this(new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndInThreads(final Func<X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(final Proc<X> proc, final Iterable<X> src) {
        this(new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(final Func<X, Boolean> func,
        final Iterable<X> src) {
        this(
            new Mapped<>(
                item -> (Scalar<Boolean>) () -> func.apply(item), src
            )
        );
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    @SafeVarargs
    public AndInThreads(final Scalar<Boolean>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public AndInThreads(final Iterable<Scalar<Boolean>> src) {
        this(Executors.newCachedThreadPool(), src, true);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndInThreads(final ExecutorService svc,
        final Proc<X> proc, final X... src) {
        this(svc, new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndInThreads(final ExecutorService svc,
        final Func<X, Boolean> func, final X... src) {
        this(svc, func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(final ExecutorService svc,
        final Proc<X> proc, final Iterable<X> src) {
        this(svc, new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(final ExecutorService svc,
        final Func<X, Boolean> func, final Iterable<X> src) {
        this(
            svc,
            new Mapped<>(
                item -> (Scalar<Boolean>) () -> func.apply(item), src
            )
        );
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     */
    @SafeVarargs
    public AndInThreads(final ExecutorService svc,
        final Scalar<Boolean>... src) {
        this(svc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     */
    public AndInThreads(final ExecutorService svc,
        final Iterable<Scalar<Boolean>> src) {
        this(svc, src, false);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     * @param sht Shut it down
     */
    private AndInThreads(final ExecutorService svc,
        final Iterable<Scalar<Boolean>> src, final boolean sht) {
        this.service = svc;
        this.iterable = src;
        this.shut = sht;
    }

    @Override
    public Boolean value() throws Exception {
        final Collection<Future<Boolean>> futures = new LinkedList<>();
        for (final Scalar<Boolean> item : this.iterable) {
            futures.add(this.service.submit(item::value));
        }
        final boolean result = new And(
            (Func<Future<Boolean>, Boolean>) Future::get,
            futures
        ).value();
        if (this.shut) {
            this.service.shutdown();
            try {
                if (!this.service.awaitTermination(1L, TimeUnit.MINUTES)) {
                    throw new IllegalStateException(
                        String.format(
                            "Can't terminate the service, result=%b",
                            result
                        )
                    );
                }
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(ex);
            }
        }
        return result;
    }

}
