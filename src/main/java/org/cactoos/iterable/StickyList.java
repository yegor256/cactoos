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
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * List decorator that goes through the list only once.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.8
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StickyList<X> implements List<X> {

    /**
     * The gate.
     */
    private final UncheckedScalar<List<X>> gate;

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public StickyList(final X... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items The array
     */
    public StickyList(final Iterable<X> items) {
        this(new ListOf<>(items));
    }

    /**
     * Ctor.
     * @param list The iterable
     */
    public StickyList(final Collection<X> list) {
        this.gate = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<X> temp = new LinkedList<>();
                    temp.addAll(list);
                    return temp;
                }
            )
        );
    }

    @Override
    public int size() {
        return this.gate.value().size();
    }

    @Override
    public boolean isEmpty() {
        return this.gate.value().isEmpty();
    }

    @Override
    public boolean contains(final Object object) {
        return this.gate.value().contains(object);
    }

    @Override
    public Iterator<X> iterator() {
        return this.gate.value().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.gate.value().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <Y> Y[] toArray(final Y[] array) {
        return this.gate.value().toArray(array);
    }

    @Override
    public boolean add(final X element) {
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
        return this.gate.value().containsAll(collection);
    }

    @Override
    public boolean addAll(final Collection<? extends X> collection) {
        throw new UnsupportedOperationException(
            "#addAll(final Collection<? extends T> collection) is not supported"
        );
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends X> collection) {
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
    public X get(final int index) {
        return this.gate.value().get(index);
    }

    @Override
    public X set(final int index, final X element) {
        throw new UnsupportedOperationException(
            "#set() is not supported"
        );
    }

    @Override
    public void add(final int index, final X element) {
        throw new UnsupportedOperationException(
            "#add(final int index, final T element) is not supported"
        );
    }

    @Override
    public X remove(final int index) {
        throw new UnsupportedOperationException(
            "#remove(final int index) is not supported"
        );
    }

    @Override
    public int indexOf(final Object object) {
        return this.gate.value().indexOf(object);
    }

    @Override
    public int lastIndexOf(final Object object) {
        return this.gate.value().lastIndexOf(object);
    }

    @Override
    public ListIterator<X> listIterator() {
        return this.gate.value().listIterator();
    }

    @Override
    public ListIterator<X> listIterator(final int index) {
        return this.gate.value().listIterator(index);
    }

    @Override
    public List<X> subList(final int fromindex, final int toindex) {
        return this.gate.value().subList(fromindex, toindex);
    }
}
