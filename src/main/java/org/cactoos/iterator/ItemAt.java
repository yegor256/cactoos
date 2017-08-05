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
package org.cactoos.iterator;

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
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.7
 */
public final class ItemAt<T> implements Scalar<T> {

    /**
     * Source iterator.
     */
    private final Iterator<T> src;

    /**
     * Fallback value.
     */
    private final Func<Iterable<T>, T> fbk;

    /**
     * Position.
     */
    private final int pos;

    /**
     * Ctor.
     *
     * @param iterator Iterator
     */
    public ItemAt(final Iterator<T> iterator) {
        this(
            iterator,
            itr -> {
                throw new IOException("Iterator is empty");
            }
        );
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param fallback Fallback value
     */
    public ItemAt(final Iterator<T> iterator, final T fallback) {
        this(iterator, itr -> fallback);
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param fallback Fallback value
     */
    public ItemAt(final Iterator<T> iterator,
        final Func<Iterable<T>, T> fallback) {
        this(iterator, 0, fallback);
    }

    /**
     * Ctor.
     *
     * @param iterator Iterator
     * @param position Position
     */
    public ItemAt(final Iterator<T> iterator, final int position) {
        this(
            iterator,
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
     */
    public ItemAt(final Iterator<T> iterator, final int position,
        final Func<Iterable<T>, T> fallback) {
        this.pos = position;
        this.src = iterator;
        this.fbk = fallback;
    }

    @Override
    public T value() throws Exception {
        if (this.pos < 0) {
            throw new IOException(
                new FormattedText(
                    "The position must be non-negative: %d",
                    this.pos
                ).asString()
            );
        }
        int cur;
        for (cur = 0; cur < this.pos && this.src.hasNext(); ++cur) {
            this.src.next();
        }
        final T ret;
        if (cur == this.pos && this.src.hasNext()) {
            ret = this.src.next();
        } else {
            ret = this.fbk.apply(() -> this.src);
        }
        return ret;
    }
}
