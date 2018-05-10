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
package org.cactoos.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable as {@link List}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link List} on each
 * method call. It doesn't cache the data anyhow. If you don't
 * need this {@link List} to re-fresh its content on every call,
 * by doing round-trips to the encapsulated iterable, decorate it with
 * {@link StickyList}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> List type
 * @see StickyList
 * @since 0.1
 */
public final class ListOf<T> extends ListEnvelope<T> {

    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    public ListOf(final T... array) {
        this(new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param src An {@link Iterator}
     * @since 0.21
     */
    public ListOf(final Iterator<T> src) {
        this(() -> src);
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    public ListOf(final Iterable<T> src) {
        super(() -> {
            final List<T> temp = new LinkedList<>();
            for (final T item : src) {
                temp.add(item);
            }
            return Collections.unmodifiableList(temp);
        });
    }

}
