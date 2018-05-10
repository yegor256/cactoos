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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Sorted list.
 *
 * <p>Pay attention that sorting will happen on each operation
 * with the collection. Every time you touch it, it will fetch the
 * entire list from the encapsulated object and sort it. If you
 * want to avoid that "side-effect", decorate it with
 * {@link StickyList}.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @see StickyList
 * @since 0.19
 */
public final class Sorted<T> extends ListEnvelope<T> {

    /**
     * Ctor.
     * @param src The underlying collection
     */
    @SafeVarargs
    public Sorted(final T... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     *
     * <p>If you're using this ctor you must be sure that type {@code T}
     * implements {@link Comparable} interface. Otherwise, there will be
     * a type casting exception in runtime.</p>
     *
     * @param src The underlying collection
     * @since 0.21
     */
    public Sorted(final Iterator<T> src) {
        this(() -> src);
    }

    /**
     * Ctor.
     *
     * <p>If you're using this ctor you must be sure that type {@code T}
     * implements {@link Comparable} interface. Otherwise, there will be
     * a type casting exception in runtime.</p>
     *
     * @param src The underlying collection
     */
    @SuppressWarnings("unchecked")
    public Sorted(final Iterable<T> src) {
        this((Comparator<T>) Comparator.naturalOrder(), new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying collection
     * @param cmp The comparator
     */
    @SafeVarargs
    public Sorted(final Comparator<T> cmp, final T... src) {
        this(cmp, new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying collection
     * @param cmp The comparator
     */
    public Sorted(final Comparator<T> cmp, final Collection<T> src) {
        super(() -> {
            final List<T> items = new ArrayList<>(src.size());
            items.addAll(src);
            items.sort(cmp);
            return Collections.unmodifiableList(items);
        }
);
    }

}
