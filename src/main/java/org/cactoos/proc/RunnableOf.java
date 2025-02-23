/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.Callable;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Func as {@link Runnable}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
public final class RunnableOf extends RunnableEnvelope {

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param <X> Type of input
     * @since 0.32
     */
    public <X> RunnableOf(final Proc<? super X> proc, final X ipt) {
        this(
            () -> new UncheckedProc<>(proc).exec(ipt)
        );
    }

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     * @since 0.11
     */
    public RunnableOf(final Scalar<?> scalar) {
        this(
            () -> {
                new Unchecked<>(scalar).value();
            }
        );
    }

    /**
     * Ctor.
     * @param callable The callable
     * @since 0.53
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public RunnableOf(final Callable<?> callable) {
        this(
            () -> {
                try {
                    callable.call();
                    // @checkstyle IllegalCatchCheck (1 line)
                } catch (final Exception ex) {
                    throw new IllegalStateException(ex);
                }
            }
        );
    }

    /**
     * Ctor.
     * @param runnable Encapsulated runnable
     * @since 0.49
     */
    public RunnableOf(final Runnable runnable) {
        super(runnable);
    }
}
