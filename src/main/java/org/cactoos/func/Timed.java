/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.cactoos.Func;

/**
 * Function that gets interrupted after a certain time has passed.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.29.3
 */
@SuppressWarnings("PMD.CloseInlineResourceRule")
public final class Timed<X, Y> implements Func<X, Y> {

    /**
     * Origin function.
     */
    private final Func<? super X, ? extends Future<? extends Y>> func;

    /**
     * Milliseconds.
     */
    private final long time;

    /**
     * Ctor.
     *
     * @param function Origin function
     * @param milliseconds Milliseconds
     */
    public Timed(final Func<? super X, ? extends Y> function, final long milliseconds) {
        this(milliseconds, new Async<X, Y>(function));
    }

    /**
     * Ctor.
     *
     * @param milliseconds Milliseconds
     * @param async Async function
     */
    public Timed(
        final long milliseconds,
        final Func<? super X, ? extends Future<? extends Y>> async
    ) {
        this.func = async;
        this.time = milliseconds;
    }

    @Override
    public Y apply(final X input) throws Exception {
        final Future<? extends Y> future = this.func.apply(input);
        try {
            return future.get(this.time, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException | ExecutionException
            | TimeoutException exp) {
            future.cancel(true);
            throw exp;
        }
    }
}
