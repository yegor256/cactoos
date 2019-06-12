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
package org.cactoos.scalar;

import java.io.IOException;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.text.FormattedText;

/**
 * Element from position in {@link Iterable}
 * or fallback value if iterable doesn't have this position.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Scalar type
 * @since 0.7
 */
public final class ItemAt<T> implements Scalar<T> {

    /**
     * {@link Sticky} that holds the value of the {@link Scalar}
     *  at the position specified in the constructor.
     */
    private final Scalar<T> saved;

    /**
     * Ctor.
     *
     * @param position Position
     * @param iterable Iterable
     */
    public ItemAt(final int position, final Iterable<T> iterable) {
        this(
            position,
            itr -> {
                throw new IOException(
                    new FormattedText(
                        "The iterable doesn't have the position #%d",
                        position
                    ).asString()
                );
            },
            iterable
        );
    }

    /**
     * Ctor.
     *
     * @param position Position
     * @param fallback Fallback value
     * @param iterable Iterable
     */
    public ItemAt(
        final int position,
        final T fallback,
        final Iterable<T> iterable
    ) {
        this(position, new FuncOf<>(fallback), iterable);
    }

    /**
     * Ctor.
     *
     * @param iterable Iterable
     * @param position Position
     * @param fallback Fallback value
     */
    public ItemAt(
        final int position,
        final Func<Iterable<T>, T> fallback,
        final Iterable<T> iterable
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
                final Iterator<T> src = iterable.iterator();
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
