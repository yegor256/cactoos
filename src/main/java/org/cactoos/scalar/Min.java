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
package org.cactoos.scalar;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Find the smaller among items.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.9
 */
public final class Min<T extends Comparable<T>> implements Scalar<T> {

    /**
     * Items.
     */
    private final Iterable<Scalar<T>> items;

    /**
     * Ctor.
     * @param scalars The items
     */
    @SafeVarargs
    public Min(final Scalar<T>... scalars) {
        this(new IterableOf<>(scalars));
    }

    /**
     * Ctor.
     * @param iterable The items
     */
    public Min(final Iterable<Scalar<T>> iterable) {
        this.items = iterable;
    }

    @Override
    public T value() throws Exception {
        final Iterator<Scalar<T>> iter = this.items.iterator();
        if (!iter.hasNext()) {
            throw new NoSuchElementException(
                "Can't find smaller element in an empty iterable"
            );
        }
        T min = iter.next().value();
        while (iter.hasNext()) {
            final T next = iter.next().value();
            if (next.compareTo(min) < 0) {
                min = next;
            }
        }
        return min;
    }

}
