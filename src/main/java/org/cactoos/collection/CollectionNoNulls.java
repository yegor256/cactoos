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
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.iterator.IteratorNoNulls;

/**
 * A decorator of {@link Collection} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Element type
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class CollectionNoNulls<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<X> col;

    /**
     * Ctor.
     * @param items Original one
     */
    public CollectionNoNulls(final Collection<X> items) {
        this.col = items;
    }

    @Override
    public int size() {
        return this.col.size();
    }

    @Override
    public boolean isEmpty() {
        return this.col.isEmpty();
    }

    @Override
    public Iterator<X> iterator() {
        return new IteratorNoNulls<X>(this.col.iterator());
    }

    @Override
    public boolean contains(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Argument of #contains(T) is NULL"
            );
        }
        return this.col.contains(item);
    }

    @Override
    public Object[] toArray() {
        final Object[] array = this.col.toArray();
        for (int idx = 0; idx < array.length; ++idx) {
            if (array[idx] == null) {
                throw new IllegalStateException(
                    String.format(
                        "Item #%d of #toArray() is NULL", idx
                    )
                );
            }
        }
        return array;
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        this.col.toArray((Object[]) array);
        for (int idx = 0; idx < array.length; ++idx) {
            if (array[idx] == null) {
                throw new IllegalStateException(
                    String.format(
                        "Item #%d of #toArray(array) is NULL", idx
                    )
                );
            }
        }
        return array;
    }

    @Override
    public boolean add(final X item) {
        if (item == null) {
            throw new IllegalStateException(
                "Item of #add(T) is NULL"
            );
        }
        return this.col.add(item);
    }

    @Override
    public boolean remove(final Object item) {
        if (item == null) {
            throw new IllegalStateException(
                "Item of #remove(T) is NULL"
            );
        }
        return this.col.remove(item);
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.col.containsAll(new CollectionNoNulls<>(items));
    }

    @Override
    public boolean addAll(final Collection<? extends X> items) {
        return this.col.removeAll(new CollectionNoNulls<>(items));
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        return this.col.removeAll(new CollectionNoNulls<>(items));
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        return this.col.retainAll(new CollectionNoNulls<>(items));
    }

    @Override
    public void clear() {
        this.col.clear();
    }
}
