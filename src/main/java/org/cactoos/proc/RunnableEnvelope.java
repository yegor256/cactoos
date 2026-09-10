/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

/**
 * Envelope for Runnable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.50
 */
public abstract class RunnableEnvelope implements Runnable {

    /**
     * Runnable to decorate.
     */
    private final Runnable origin;

    /**
     * Ctor.
     * @param runnable The Runnable
     */
    public RunnableEnvelope(final Runnable runnable) {
        this.origin = runnable;
    }

    @Override
    public final void run() {
        this.origin.run();
    }
}
