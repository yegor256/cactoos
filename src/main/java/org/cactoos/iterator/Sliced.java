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
import java.util.function.IntPredicate;

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
    private IntPredicate end;

    /**
     * Constructor.
     * @param start Starting index
     * @param count Maximum number of elements for resulted iterator
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final int count,
        final Iterator<T> iterator) {
        this(start, index -> index > start + count - 1, iterator);
    }

    /**
     * Constructor.
     * Constructs an iterator of start position and up to the end
     * @param start Starting index
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final Iterator<T> iterator) {
        this(start, any -> !iterator.hasNext(), iterator);
    }

    /**
     * Constructor.
     * @param start Starting index
     * @param end Predicate that test whether iterating should stop
     * @param iterator Decorated iterator
     */
    private Sliced(final int start, final IntPredicate end,
        final Iterator<T> iterator) {
        this.start = start;
        this.end = end;
        this.iterator = iterator;
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        this.skip();
        return !this.end.test(this.current) && this.iterator.hasNext();
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
}
