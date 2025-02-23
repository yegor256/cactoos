/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
import org.cactoos.text.FormattedText;

/**
 * Logical conjunction, in multiple threads.
 *
 * <p>The usage is same as for {@link And}</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see Unchecked
 * @see IoChecked
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
    private final Iterable<? extends Scalar<Boolean>> iterable;

    /**
     * Shut down the service when it's done.
     */
    private final boolean shut;

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndInThreads(final Func<? super X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(final Func<? super X, Boolean> func,
        final Iterable<? extends X> src) {
        this(
            new Mapped<>(
                item -> new ScalarOf<>(() -> func.apply(item)),
                src
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
    public AndInThreads(final Iterable<? extends Scalar<Boolean>> src) {
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
    public <X> AndInThreads(
        final ExecutorService svc,
        final Proc<? super X> proc,
        final X... src
    ) {
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
    public <X> AndInThreads(
        final ExecutorService svc,
        final Func<? super X, Boolean> func,
        final X... src
    ) {
        this(svc, func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param proc Proc to use
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(
        final ExecutorService svc,
        final Proc<? super X> proc,
        final Iterable<? extends X> src
    ) {
        this(svc, new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    public <X> AndInThreads(
        final ExecutorService svc,
        final Func<? super X, Boolean> func,
        final Iterable<? extends X> src
    ) {
        this(
            svc,
            new Mapped<>(
                item -> new ScalarOf<>(() -> func.apply(item)),
                src
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
        final Iterable<? extends Scalar<Boolean>> src) {
        this(svc, src, false);
    }

    /**
     * Ctor.
     * @param svc Executable service to run thread in
     * @param src The iterable
     * @param sht Shut it down
     */
    private AndInThreads(final ExecutorService svc,
        final Iterable<? extends Scalar<Boolean>> src, final boolean sht) {
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
            Future::get,
            futures
        ).value();
        if (this.shut) {
            this.service.shutdown();
            try {
                if (!this.service.awaitTermination(1L, TimeUnit.MINUTES)) {
                    throw new IllegalStateException(
                        new FormattedText(
                            "Can't terminate the service, result=%b",
                            result
                        ).asString()
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
