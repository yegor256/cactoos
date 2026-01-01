/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.iterator.IteratorOfStackTrace;

/**
 * Iterable of exception.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.56
 */
public final class IterableOfStackTrace extends IterableEnvelope<Throwable> {

    /**
     * Ctor.
     * @param exc The exception to iterate.
     */
    public IterableOfStackTrace(final Throwable exc) {
        super(new IterableOf<>(() -> new IteratorOfStackTrace(exc)));
    }
}
