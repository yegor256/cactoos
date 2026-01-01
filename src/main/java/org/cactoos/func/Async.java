/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Func that runs in the background.
 *
 * <p>If you want your piece of code to be executed in the background,
 * use {@link Async} as following:</p>
 *
 * <pre> int length = new AsyncFunc(
 *   input -&gt; input.length()
 * ).apply("Hello, world!").get();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.10
 */
public final class Async<X, Y> implements Func<X, Future<Y>>, Proc<X> {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * The executor service.
     */
    private final ExecutorService executor;

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
        this(fnc, Executors.newSingleThreadExecutor(fct));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param exec Executor Service
     */
    public Async(final Func<X, Y> fnc, final ExecutorService exec) {
        this.func = fnc;
        this.executor = exec;
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
}
