/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
    private final Iterator<? extends T> iterator;

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
        final Iterator<? extends T> iterator) {
        this(start, index -> index > start + count - 1, iterator);
    }

    /**
     * Constructor.
     * Constructs an iterator of start position and up to the end
     * @param start Starting index
     * @param iterator Decorated iterator
     */
    public Sliced(final int start, final Iterator<? extends T> iterator) {
        this(start, any -> !iterator.hasNext(), iterator);
    }

    /**
     * Constructor.
     * @param start Starting index
     * @param end Predicate that test whether iterating should stop
     * @param iterator Decorated iterator
     */
    private Sliced(final int start, final IntPredicate end,
        final Iterator<? extends T> iterator) {
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
