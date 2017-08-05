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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Iterable as {@link List}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link List} on each
 * method call. It doesn't cache the data anyhow.</p>
 *
 * <p>If you don't need this {@link List} to re-fresh its content on every call,
 * by doing round-trips to the encapsulated iterable, use
 * {@link StickyList}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Alexey Semenyuk (semenyukalexey88@gmail.com)
 * @author Kirill (g4s8.public@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> List type
 * @see StickyList
 * @since 0.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ListOf<T> implements List<T> {

    /**
     * The source.
     */
    private final Iterable<T> iterable;

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
     *
     * @param src An {@link Iterable}
     */
    public ListOf(final Iterable<T> src) {
        this.iterable = src;
    }

    @Override
    public int size() {
        return new LengthOf(this.iterable).value().intValue();
    }

    @Override
    public boolean isEmpty() {
        return !this.iterable.iterator().hasNext();
    }

    @Override
    public boolean contains(final Object object) {
        return this.list().contains(object);
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterable.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <Y> Y[] toArray(final Y[] array) {
        return this.list().toArray(array);
    }

    @Override
    public boolean add(final T element) {
        throw new UnsupportedOperationException(
            "#add(final T element) is not supported"
        );
    }

    @Override
    public boolean remove(final Object object) {
        throw new UnsupportedOperationException(
            "#remove(final Object object) is not supported"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        return this.list().containsAll(collection);
    }

    @Override
    public boolean addAll(final Collection<? extends T> collection) {
        throw new UnsupportedOperationException(
            "#addAll(final Collection<? extends T> collection) is not supported"
        );
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends T> collection) {
        throw new UnsupportedOperationException(
            "#addAll() is not supported"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        throw new UnsupportedOperationException(
            "#removeAll(final Collection<?> collection) is not supported"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> collection) {
        throw new UnsupportedOperationException(
            "#retainAll(final Collection<?> collection) is not supported"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear() is not supported"
        );
    }

    @Override
    public T get(final int index) {
        return this.list().get(index);
    }

    @Override
    public T set(final int index, final T element) {
        throw new UnsupportedOperationException(
            "#set() is not supported"
        );
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException(
            "#add(final int index, final T element) is not supported"
        );
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException(
            "#remove(final int index) is not supported"
        );
    }

    @Override
    public int indexOf(final Object object) {
        return this.list().indexOf(object);
    }

    @Override
    public int lastIndexOf(final Object object) {
        return this.list().lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return this.list().listIterator(index);
    }

    @Override
    public List<T> subList(final int from, final int end) {
        return this.list().subList(from, end);
    }

    /**
     * Build a list.
     * @return List
     */
    private List<T> list() {
        final List<T> list = new LinkedList<>();
        for (final T item : this.iterable) {
            list.add(item);
        }
        return list;
    }
}
