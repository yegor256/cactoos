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
package org.cactoos.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link List} envelope that doesn't allow mutations.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 1.16
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class Immutable<T> implements List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param src Source
     */
    public Immutable(final List<T> src) {
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
        return this.list.contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new org.cactoos.iterator.Immutable<>(this.list.iterator());
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return this.list.toArray(array);
    }

    @Override
    public boolean add(final T item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.list.containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(): the collection is read-only"
        );
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> items) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#removeAll(): the collection is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#retainAll(): the collection is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the collection is read-only"
        );
    }

    @Override
    public T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public T set(final int index, final T item) {
        throw new UnsupportedOperationException("#set()");
    }

    @Override
    public void add(final int index, final T item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public int indexOf(final Object item) {
        return this.list.indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        return this.list.lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIteratorOf<>(
            this.list.listIterator()
        );
    }

    /**
     * @todo #898:30min Introduce an Immutable wrapper for ListIterator
     * and use it here and above instead of ListIteratorOf.
     * One another option is renaming of ListIteratorOf.
     */
    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ListIteratorOf<>(
            this.list.listIterator(index)
        );
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return new Immutable<>(this.list.subList(start, end));
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
