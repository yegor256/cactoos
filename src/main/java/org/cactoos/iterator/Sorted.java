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
package org.cactoos.iterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Sorted iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.7
 */
public final class Sorted<T> implements Iterator<T> {

    /**
     * Sorted one.
     */
    private final UncheckedScalar<Iterator<T>> scalar;

    /**
     * Ctor.
     *
     * <p>If you're using this ctor you must be sure that type {@code T}
     * implements {@link Comparable} interface. Otherwise, there will be
     * a type casting exception in runtime.</p>
     *
     * @param items The underlying iterator
     */
    @SuppressWarnings("unchecked")
    public Sorted(final Iterator<T> items) {
        this((Comparator<T>) Comparator.naturalOrder(), items);
    }

    /**
     * Ctor.
     * @param iterator The underlying iterator
     * @param comparator The comparator
     */
    public Sorted(final Comparator<T> comparator, final Iterator<T> iterator) {
        this.scalar = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<T> items = new LinkedList<>();
                    while (iterator.hasNext()) {
                        items.add(iterator.next());
                    }
                    items.sort(comparator);
                    return items.iterator();
                }
            )
        );
    }

    @Override
    public boolean hasNext() {
        return this.scalar.value().hasNext();
    }

    @Override
    public T next() {
        return this.scalar.value().next();
    }
}
