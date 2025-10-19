/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.exception;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterator.IteratorOfStackTrace;
import org.cactoos.iterator.TailOf;

/**
 * Return root cause exception.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.56
 */
public final class RootCause implements Scalar<Throwable> {

    /**
     * Iterator for exceptions.
     */
    private final Scalar<Iterator<Throwable>> itr;

    /**
     * Ctor.
     * @param exc The exception to iterate.
     */
    public RootCause(final Throwable exc) {
        this(new IteratorOfStackTrace(exc));
    }

    /**
     * Ctor.
     * @param iter The exception Iterator.
     */
    public RootCause(final Iterator<Throwable> iter) {
        this(() -> new TailOf<>(1, iter));
    }

    /**
     * Ctor.
     * @param itr Decorated iterator.
     */
    public RootCause(final Scalar<Iterator<Throwable>> itr) {
        this.itr = itr;
    }

    @Override
    public Throwable value() throws Exception {
        return this.itr.value().next();
    }
}
