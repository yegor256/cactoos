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
