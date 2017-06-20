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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
 * @todo #180:30min This class is not implemented fully. Most methods
 *  are not yet supported. Let's implement them, each time with a use
 *  case specific method. And let's make sure they all are tested.
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class IterableAsList<T> implements List<T> {

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
    @SuppressWarnings("varargs")
    public IterableAsList(final T... array) {
        this(new ArrayAsIterable<>(array));
    }

    /**
     * Ctor.
     *
     * @param src An {@link Iterable}
     */
    public IterableAsList(final Iterable<T> src) {
        this.iterable = src;
    }

    @Override
    public int size() {
        return new LengthOfIterable(this.iterable).asValue();
    }

    @Override
    public boolean isEmpty() {
        return !this.iterable.iterator().hasNext();
    }

    @Override
    public boolean contains(final Object object) {
        throw new UnsupportedOperationException(
            "#contains() not implemented yet"
        );
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterable.iterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException(
            "#toArray() not implemented yet"
        );
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <Y> Y[] toArray(final Y[] array) {
        throw new UnsupportedOperationException(
            "#toArray(array) not implemented yet"
        );
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
        throw new UnsupportedOperationException(
            "#containsAll() not implemented yet"
        );
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
        throw new UnsupportedOperationException(
            "#get() not implemented yet"
        );
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
        throw new UnsupportedOperationException(
            "#indexOf() not implemented yet"
        );
    }

    @Override
    public int lastIndexOf(final Object object) {
        throw new UnsupportedOperationException(
            "#lastIndexOf() not implemented yet"
        );
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException(
            "#listIterator() not implemented yet"
        );
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        throw new UnsupportedOperationException(
            "#listIterator(index) not implemented yet"
        );
    }

    @Override
    public List<T> subList(final int fromindex, final int toindex) {
        throw new UnsupportedOperationException(
            "#subList() not implemented yet"
        );
    }
}
