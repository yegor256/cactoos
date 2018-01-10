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
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorator of an {@link Iterator} that returns no NULL.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.27
 */
public final class IteratorNoNulls<X> implements Iterator<X> {

    /**
     * Iterator.
     */
    private final Iterator<X> iterator;

    /**
     * Position.
     */
    private final AtomicLong pos;

    /**
     * Ctor.
     * @param src Source iterable
     */
    public IteratorNoNulls(final Iterator<X> src) {
        this.iterator = src;
        this.pos = new AtomicLong();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public X next() {
        final X next = this.iterator.next();
        if (next == null) {
            throw new IllegalStateException(
                String.format(
                    "Item #%d of %s is NULL",
                    this.pos.get(), this.iterator
                )
            );
        }
        this.pos.incrementAndGet();
        return next;
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }

}
