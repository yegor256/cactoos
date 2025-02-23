/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.Callable;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Func as {@link Callable}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of return
 * @since 0.53
 */
public final class CallableOf<T> implements Callable<T> {

    /**
     * The callable.
     */
    private final Callable<T> callable;

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param <X> Type of input
     * @since 0.32
     */
    public <X> CallableOf(final Proc<? super X> proc, final X ipt) {
        this(
            () -> new UncheckedProc<>(proc).exec(ipt)
        );
    }

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     * @since 0.11
     */
    public CallableOf(final Scalar<?> scalar) {
        this(
            () -> {
                new Unchecked<>(scalar).value();
            }
        );
    }

    /**
     * Ctor.
     * @param runnable The callable
     * @since 0.53
     */
    public CallableOf(final Runnable runnable) {
        this(
            () -> {
                runnable.run();
                return null;
            }
        );
    }

    /**
     * Ctor.
     * @param clbl The callable original
     * @since 0.53
     */
    public CallableOf(final Callable<T> clbl) {
        this.callable = clbl;
    }

    @Override
    public T call() throws Exception {
        return this.callable.call();
    }
}
