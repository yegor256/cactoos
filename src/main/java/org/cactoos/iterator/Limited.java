/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
 * Limited origin.
 *
 * <p>This is a decorator over an existing origin. Returns elements of the
 * original origin, until either the requested number of items have been
 * returned or the underlying origin has been exhausted.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.6
 */
public final class Limited<T> implements Iterator<T> {

    /**
     * Decorated origin.
     */
    private final Iterator<T> origin;

    /**
     * Number of elements to return.
     */
    private final int restrict;

    /**
     * Number of elements returned so far.
     */
    private int consumed;

    /**
     * Ctor.
     *
     * @param limit The requested number of elements
     * @param iterator The underlying iterator
     */
    public Limited(final int limit, final Iterator<T> iterator) {
        this.origin = iterator;
        this.restrict = limit;
        this.consumed = 0;
    }

    @Override
    public boolean hasNext() {
        return this.consumed < this.restrict && this.origin.hasNext();
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more elements.");
        }
        ++this.consumed;
        return this.origin.next();
    }
}
