/*
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
package org.cactoos.scalar;

import java.io.IOException;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;

/**
 * Element from position in {@link Iterator}
 * or fallback value if iterator hasn't this position.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Scalar type
 * @since 0.7
 * @todo #930:30min To be consistent with other objects working on iterables
 *  such as HeadOf, TailOf, etc, the constructor of ItemAt should always take
 *  the iterable/iterator as a last parameter.
 */
public final class ItemAt<T> implements Scalar<T> {

    /**
     * {@link Sticky} that holds the value of the iterator
     *  at the position specified in the constructor.
     */
    private final Scalar<T> saved;

    /**
     * Ctor.
     *
     * @param source Iterable
     */
    public ItemAt(final Iterable<T> source) {
        this(
            source,
            itr -> {
                throw new IOException("The iterable is empty");
            }
        );
    }

    /**
     * Ctor.
     *
     * @param fallback Fallback value
     * @param source Iterable
     */
    public ItemAt(final T fallback, final Iterable<T> source) {
        this(source, itr -> fallback);
    }

    /**
     * Ctor.
     *
     * @param fallback Fallback value
     * @param source Iterable
     */
    public ItemAt(
        final Iterable<T> source,
        final Func<Iterable<T>, T> fallback
    ) {
        this(source, 0, fallback);
    }

    /**
     * Ctor.
     *
     * @param position Position
     * @param source Iterable
     */
    public ItemAt(final int position, final Iterable<T> source) {
        this(
            source,
            position,
            itr -> {
                throw new IOException(
                    new FormattedText(
                        "The iterable doesn't have the position #%d",
                        position
                    ).asString()
                );
            }
        );
    }

    /**
     * Ctor.
     *
     * @param source Iterable
     * @param position Position
     * @param fallback Fallback value
     */
    public ItemAt(
        final Iterable<T> source,
        final int position,
        final Func<Iterable<T>, T> fallback
    ) {
        this(new Sticky<>(source::iterator), position, fallback);
    }

    /**
     * Ctor.
     *
     * @param iterable Iterable
     * @param fallback Fallback value
     */
    public ItemAt(final Iterable<T> iterable, final T fallback) {
        this(iterable, itr -> fallback);
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param fallback Fallback value
     */
    public ItemAt(
        final Iterator<T> iterator,
        final Func<Iterable<T>, T> fallback
    ) {
        this(iterator, 0, fallback);
    }

    /**
     * Ctor.
     *
     * @param iterable Iterable
     * @param position Position
     */
    public ItemAt(final Iterable<T> iterable, final int position) {
        this(
            iterable.iterator(),
            position,
            itr -> {
                throw new IOException(
                    new FormattedText(
                        "Iterator doesn't have an element at #%d position",
                        position
                    ).asString()
                );
            }
        );
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param position Position
     * @param fallback Fallback value
     * @todo #911:30min Remove iterator constructor
     *  from org.cactoos.scalar.ItemAt.
     *  We need to avoid the usage of Iterators
     *  and replace all of their occurrences in ctors with Iterables.
     */
    public ItemAt(
        final Iterator<T> iterator,
        final int position,
        final Func<Iterable<T>, T> fallback
    ) {
        this(new Sticky<>(() -> iterator), position, fallback);
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param position Position
     * @param fallback Fallback value
     */
    private ItemAt(
        final Scalar<Iterator<T>> iterator,
        final int position,
        final Func<Iterable<T>, T> fallback
    ) {
        this.saved = new Sticky<T>(
            () -> {
                final T ret;
                if (position < 0) {
                    throw new IOException(
                        new FormattedText(
                            "The position must be non-negative: %d",
                            position
                        ).asString()
                    );
                }
                final Iterator<T> src = iterator.value();
                int cur;
                for (cur = 0; cur < position && src.hasNext(); ++cur) {
                    src.next();
                }
                if (cur == position && src.hasNext()) {
                    ret = src.next();
                } else {
                    ret = fallback.apply(() -> src);
                }
                return ret;
            }
        );
    }

    @Override
    public T value() throws Exception {
        return this.saved.value();
    }

}
