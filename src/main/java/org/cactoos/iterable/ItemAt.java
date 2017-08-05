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
package org.cactoos.iterable;

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;

/**
 * Element from position in {@link Iterable}
 * or fallback value if iterable hasn't this position.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.7
 */
public final class ItemAt<T> implements Scalar<T> {

    /**
     * Source iterable.
     */
    private final Iterable<T> src;

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
     * @param source Iterable
     * @param fallback Fallback value
     */
    public ItemAt(final Iterable<T> source, final T fallback) {
        this(source, itr -> fallback);
    }

    /**
     * Ctor.
     *
     * @param source Iterable
     * @param fallback Fallback value
     */
    public ItemAt(final Iterable<T> source,
        final Func<Iterable<T>, T> fallback) {
        this(source, 0, fallback);
    }

    /**
     * Ctor.
     *
     * @param source Iterable
     * @param position Position
     */
    public ItemAt(final Iterable<T> source, final int position) {
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
    public ItemAt(final Iterable<T> source, final int position,
        final Func<Iterable<T>, T> fallback) {
        this.pos = position;
        this.src = source;
        this.fbk = fallback;
    }

    @Override
    public T value() throws Exception {
        return new org.cactoos.iterator.ItemAt<>(
            this.src.iterator(), this.pos, this.fbk
        ).value();
    }
}
