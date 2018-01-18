/**
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.collection.CollectionNoNulls;

/**
 * A decorator of {@link List} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ListNoNulls<T> implements List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param src Source
     */
    ListNoNulls(final List<T> src) {
        this.list = src;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(final Object item) {
        return new CollectionNoNulls<>(this.list).contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new CollectionNoNulls<>(this.list).iterator();
    }

    @Override
    public Object[] toArray() {
        return new CollectionNoNulls<>(this.list).toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return new CollectionNoNulls<>(this.list).toArray(array);
    }

    @Override
    public boolean add(final T item) {
        return new CollectionNoNulls<>(this.list).add(item);
    }

    @Override
    public boolean remove(final Object item) {
        return new CollectionNoNulls<>(this.list).remove(item);
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return new CollectionNoNulls<>(this.list).containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        return new CollectionNoNulls<>(this.list).addAll(items);
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends T> items) {
        return this.list.addAll(index, new CollectionNoNulls<>(items));
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        return new CollectionNoNulls<>(this.list).removeAll(items);
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        return new CollectionNoNulls<>(this.list).retainAll(items);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(final int index) {
        final T item = this.list.get(index);
        if (item == null) {
            throw new IllegalStateException(
                String.format(
                    "Item #%d of %s is NULL",
                    index, this.list
                )
            );
        }
        return item;
    }

    @Override
    public T set(final int index, final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Item can't be NULL in #set(%d,T)",
                    index
                )
            );
        }
        final T result = this.list.set(index, item);
        if (result == null) {
            throw new IllegalStateException(
                String.format(
                    "Result of #set(%d,T) is NULL",
                    index
                )
            );
        }
        return result;
    }

    @Override
    public void add(final int index, final T item) {
        if (item == null) {
            throw new IllegalArgumentException(
                String.format(
                    "Item can't be NULL in #add(%d,T)",
                    index
                )
            );
        }
        this.list.add(index, item);
    }

    @Override
    public T remove(final int index) {
        final T result = this.list.remove(index);
        if (result == null) {
            throw new IllegalStateException(
                String.format(
                    "Result of #remove(%d) is NULL",
                    index
                )
            );
        }
        return result;
    }

    @Override
    public int indexOf(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #indexOf(T)"
            );
        }
        return this.list.indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        if (item == null) {
            throw new IllegalArgumentException(
                "Item can't be NULL in #lastIndexOf(T)"
            );
        }
        return this.list.lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return this.list.listIterator(index);
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return new ListNoNulls<>(this.list.subList(start, end));
    }
}
