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
import java.util.LinkedList;
import org.cactoos.collection.CollectionOf;

/**
 * Tail portion of the iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> implements Iterator<T> {

    /**
     * Decorated iterator.
     */
    private final Iterator<T> origin;

    /**
     * Ctor.
     * @param iterator Decorated iterator
     * @param num Number of tail elements
     */
    public TailOf(final int num, final Iterator<T> iterator) {
        this.origin = new LinkedList<>(
            new CollectionOf<>(
                new HeadOf<>(
                    num,
                    new LinkedList<>(
                        new CollectionOf<>(iterator)
                    ).descendingIterator()
                )
            )
        ).descendingIterator();
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public T next() {
        return this.origin.next();
    }
}
