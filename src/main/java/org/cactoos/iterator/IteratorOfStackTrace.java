/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
