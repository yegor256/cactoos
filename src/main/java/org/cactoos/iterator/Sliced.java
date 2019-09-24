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
import java.util.function.Predicate;

/**
 * Creates an iterator returning an interval(slice) of the original iterator
 * by means of providing starting index, number of elements to retrieve from
 * the starting index and a decorated original iterator.
 * Can be used to retrieve the head, tail or a portion in the middle of the
 * original iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @param <T> The type of the iterator.
 * @since 1.0.0
 * @todo #1188:30min This class to be refactored by extending IteratorEnvelope,
 *  which will provide extra functionality to subclasses and is planned to be
 *  added to Cactoos shortly.
 */
public final class Sliced<T> implements Iterator<T> {

    /**
     * Decorated iterator.
     */
    private final Iterator<T> iterator;

    /**
     * First index of the resulted iterator.
     */
    private final int start;

    /**
     * Current index on the original iterator.
     */
    private int current;

    /**
     * Predicate which tests whether the end has been reached.
     */
    private Predicate<Integer> end;

    /**
     * Constructor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final int count,
        final Iterator<T> iterator) {
        this(start, iterator, index -> index > start + count - 1);
    }

    /**
     * Constructor.
     * Constructs a head iterator with a number of heading elements
     * @param iterator Decorated iterator
     * @param count Maximum number of elements for resulted iterator
     */
    public Sliced(final Iterator<T> iterator, final int count) {
        this(0, count, iterator);
    }

    /**
     * Constructor.
     * Constructs an iterator of start position and up to the end
     * @param start Starting index
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final Iterator<T> iterator) {
        this(start, iterator, any -> !iterator.hasNext());
    }

    /**
     * Constructor.
     * @param start Starting index
     * @param iterator Decorated iterator
     * @param end Predicate that test whether iterating should stop
     */
    private Sliced(final int start, final Iterator<T> iterator,
        final Predicate<Integer> end) {
        this.start = start;
        this.end = end;
        this.iterator = iterator;
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        this.skip();
        return !this.ended() && this.iterator.hasNext();
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have items any more"
            );
        }
        ++this.current;
        return this.iterator.next();
    }

    /**
     * Skips head elements up to start index.
     */
    private void skip() {
        while (this.current < this.start && this.iterator.hasNext()) {
            this.iterator.next();
            ++this.current;
        }
    }

    /**
     * Test whether the end has been reached.
     * @return Returns <tt>true</tt> if the count of elements to retrieve
     *  has been iterated over or original iterator has been iterated over,
     *  otherwise <tt>false</tt>
     */
    private boolean ended() {
        return this.end.test(this.current);
    }
}
