/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.list;

import java.util.Iterator;

/**
 * Limited iterable.
 *
 * <p>This is a view of an existing iterable containing the given number of its
 * first elements.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.6
 */
public final class LimitedIterable<T> implements Iterable<T> {

    /**
     * Decorated iterable.
     */
    private final Iterable<T> iterable;

    /**
     * Number of elements to return.
     */
    private final int limit;

    /**
     * Ctor.
     *
     * @param iterable The underlying iterable
     * @param limit The requested number of elements
     */
    public LimitedIterable(final Iterable<T> iterable, final int limit) {
        this.iterable = iterable;
        this.limit = limit;
    }

    @Override
    public Iterator<T> iterator() {
        return new LimitedIterator<>(this.iterable.iterator(), this.limit);
    }
}
