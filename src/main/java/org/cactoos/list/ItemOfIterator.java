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
public final class ItemOfIterator<T> implements Scalar<T> {

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
     * @param src Iterator
     */
    public ItemOfIterator(final Iterator<T> src) {
        this(
            src,
            itr -> {
                throw new IOException(
                    new FormattedText("Iterator %s is empty", itr.iterator())
                        .asString()
                );
            }
        );
    }

    /**
     * Ctor.
     *
     * @param src Iterator
     * @param fbk Fallback value
     */
    public ItemOfIterator(final Iterator<T> src, final T fbk) {
        this(src, itr -> fbk);
    }

    /**
     * Ctor.
     *
     * @param src Iterator
     * @param fbk Fallback value
     */
    public ItemOfIterator(
        final Iterator<T> src,
        final Func<Iterable<T>, T> fbk
    ) {
        this(src, 0, fbk);
    }

    /**
     * Ctor.
     *
     * @param src Iterator
     * @param pos Position
     */
    public ItemOfIterator(final Iterator<T> src, final int pos) {
        this(
            src,
            pos,
            itr -> {
                throw new IOException(
                    new FormattedText(
                        "Iterator %s hasn't element from position %d",
                        itr.iterator(),
                        pos
                    ).asString()
                );
            }
        );
    }

    /**
     * Ctor.
     *
     * @param src Iterator
     * @param pos Position
     * @param fbk Fallback value
     */
    public ItemOfIterator(
        final Iterator<T> src,
        final int pos,
        final Func<Iterable<T>, T> fbk
    ) {
        this.pos = pos;
        this.src = src;
        this.fbk = fbk;
    }

    @Override
    public T asValue() throws Exception {
        if (this.pos < 0) {
            throw new IOException(
                new FormattedText(
                    "Position must be nonnegative! But position = %d",
                    this.pos
                ).asString()
            );
        }
        final T ret;
        int cur;
        for (cur = 0; cur < this.pos && this.src.hasNext(); ++cur) {
            this.src.next();
        }
        if (cur == this.pos && this.src.hasNext()) {
            ret = this.src.next();
        } else {
            ret = this.fbk.apply(() -> this.src);
        }
        return ret;
    }
}
