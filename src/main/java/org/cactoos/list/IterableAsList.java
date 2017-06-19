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
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.func.StickyScalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Iterable as {@link List}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Alexey Semenyuk (semenyukalexey88@gmail.com)
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> List type
 * @since 0.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class IterableAsList<T> implements List<T> {

    /**
     * List source.
     */
    private final UncheckedScalar<List<T>> list;

    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public IterableAsList(final T... array) {
        this(new ArrayAsIterable<>(array));
    }

    /**
     * Ctor.
     *
     * @param iterable An {@link Iterable}
     */
    public IterableAsList(final Iterable<T> iterable) {
        this.list = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<T> temp = new ArrayList<>(0);
                    for (final T elem : iterable) {
                        temp.add(elem);
                    }
                    return temp;
                }
            )
        );
    }

    @Override
    public int size() {
        return this.list.asValue().size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.asValue().isEmpty();
    }

    @Override
    public boolean contains(final Object object) {
        return this.list.asValue().contains(object);
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.asValue().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list.asValue().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <Y> Y[] toArray(final Y[] array) {
        return this.list.asValue().toArray(array);
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
        return this.list.asValue().containsAll(collection);
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
        return this.list.asValue().get(index);
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
        return this.list.asValue().indexOf(object);
    }

    @Override
    public int lastIndexOf(final Object object) {
        return this.list.asValue().lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list.asValue().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return this.list.asValue().listIterator(index);
    }

    @Override
    public List<T> subList(final int fromindex, final int toindex) {
        return this.list.asValue().subList(fromindex, toindex);
    }
}
