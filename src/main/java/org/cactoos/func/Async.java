/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func that runs in the background.
 *
 * <p>If you want your piece of code to be executed in the background,
 * use {@link Async} as following:</p>
 *
 * <pre> try (Async&lt;String, Integer&gt; async = new Async&lt;&gt;(
 *   input -&gt; input.length()
 * )) {
 *   int length = async.apply("Hello, world!").get();
 * }</pre>
 *
 * <p>When {@link Async} is created with convenience constructors
 * (without an explicit {@link ExecutorService}), the internally
 * created executor will be shut down when {@link #close()} is
 * called. When an external {@link ExecutorService} is provided,
 * the caller retains ownership and is responsible for shutting
 * it down.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.10
 */
public final class Async<X, Y> implements
    Func<X, Future<Y>>, Proc<X>, Closeable {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * The executor service.
     */
    private final ExecutorService executor;

    /**
     * Shut down the executor service on close.
     */
    private final boolean shutdown;

    /**
     * Ctor.
     * @param fnc The func
     */
    public Async(final Func<X, Y> fnc) {
        this(fnc, Executors.defaultThreadFactory());
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fct Factory
     */
    public Async(final Func<X, Y> fnc, final ThreadFactory fct) {
        this(fnc, Executors.newSingleThreadExecutor(fct), true);
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param exec Executor Service
     */
    public Async(final Func<X, Y> fnc, final ExecutorService exec) {
        this(fnc, exec, false);
    }

    /**
     * Primary ctor.
     * @param fnc The func
     * @param exec Executor Service
     * @param sht Shut it down on close
     */
    private Async(
        final Func<X, Y> fnc,
        final ExecutorService exec,
        final boolean sht
    ) {
        this.func = fnc;
        this.executor = exec;
        this.shutdown = sht;
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

    @Override
    public void close() {
        if (this.shutdown) {
            this.executor.shutdown();
            try {
                if (!this.executor.awaitTermination(1L, TimeUnit.MINUTES)) {
                    this.executor.shutdownNow();
                }
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                this.executor.shutdownNow();
            }
        }
    }
}
