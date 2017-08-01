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

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.func.StickyScalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Sorted iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.7
 */
public final class
    Sorted<T extends Comparable<? super T>> implements
    Iterator<T> {

    /**
     * Sorted one.
     */
    private final UncheckedScalar<Iterator<T>> scalar;

    /**
     * Ctor.
     * @param src The underlying iterator
     */
    public Sorted(final Iterator<T> src) {
        this(Comparator.naturalOrder(), src);
    }

    /**
     * Ctor.
     * @param src The underlying iterator
     * @param cmp The comparator
     */
    public Sorted(final Comparator<T> cmp, final Iterator<T> src) {
        this.scalar = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<T> items = new LinkedList<>();
                    while (src.hasNext()) {
                        items.add(src.next());
                    }
                    items.sort(cmp);
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
