/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
 * Skipped iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.34
 * @todo #1188:30min Change the implementation of this class with the help of
 *  <tt>org.cactoos.iterator.Sliced</tt> decorator, as it contains specific
 *  constructor to build skipped iterator.
 */
public final class Skipped<T> implements Iterator<T> {

    /**
     * Decorated iterator.
     */
    private final Iterator<T> origin;

    /**
     * Count skip elements.
     */
    private int skip;

    /**
     * Ctor.
     * @param iterator Decorated iterator
     * @param skp Count skip elements
     */
    public Skipped(final int skp, final Iterator<T> iterator) {
        this.origin = iterator;
        this.skip = skp;
    }

    @Override
    public boolean hasNext() {
        this.omit();
        return this.origin.hasNext();
    }

    @Override
    public T next() {
        this.omit();
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have items any more"
            );
        }
        return this.origin.next();
    }

    /**
     * Skip first N items.
     */
    private void omit() {
        while (this.skip > 0 && this.origin.hasNext()) {
            this.origin.next();
            --this.skip;
        }
    }
}
