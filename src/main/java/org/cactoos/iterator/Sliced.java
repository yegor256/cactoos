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

/**
 * Creates an iterator returning an interval(slice) of the original iterator
 * by means of providing starting index, number of elements to retrieve from
 * the starting index and a decorated original iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @param <T> The type of the iterator.
 * @since 1.0.0
 * @todo #1190:30min This class to be refactored by extending IteratorEnvelope
 */
public final class Sliced<T> implements Iterator<T> {

    /**
     * Decorated iterator.
     */
    private final Iterator<T> iterator;

    /**
     * Constructor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final int count,
        final Iterator<T> iterator) {
        this.iterator = new HeadOf<>(
            count,
            new Skipped<>(
                start,
                iterator
            )
        );
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        return this.iterator.next();
    }
}
