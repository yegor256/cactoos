/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.Callable;

/**
 * Envelope for Callable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of output
 * @since 0.50
 */
public abstract class CallableEnvelope<T> implements Callable<T> {

    /**
     * Callable to decorate.
     */
    private final Callable<? extends T> origin;

    /**
     * Ctor.
     * @param callable The Callable
     */
    public CallableEnvelope(final Callable<? extends T> callable) {
        this.origin = callable;
    }

    @Override
    public final T call() throws Exception {
        return this.origin.call();
    }
}
