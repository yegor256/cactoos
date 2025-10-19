/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link Iterator} that returns an exception from the exception stack trace.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.56
 */
public final class IteratorOfStackTrace implements Iterator<Throwable> {

    /**
     * The exception to iterate.
     */
    private Throwable exception;

    /**
     * Ctor.
     * @param exc The exception to iterate.
     */
    public IteratorOfStackTrace(final Throwable exc) {
        this.exception = exc;
    }

    @Override
    public boolean hasNext() {
        return this.exception.getCause() != null;
    }

    @Override
    public Throwable next() {
        if (this.hasNext()) {
            this.exception = this.exception.getCause();
            return this.exception;
        }
        throw new NoSuchElementException("The iterator doesn't have item");
    }
}
