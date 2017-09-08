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
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Collection decorator that goes through the list only once.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Mykola Yashchenko (vkont4@gmail.com)
 * @version $Id$
 * @param <E> Type of item
 * @since 0.16
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class StickyCollection<E> implements Collection<E> {

    /**
     * The gate.
     */
    private final UncheckedScalar<List<E>> gate;

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public StickyCollection(final E... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items The array
     */
    public StickyCollection(final Iterable<E> items) {
        this(new ListOf<>(items));
    }

    /**
     * Ctor.
     * @param list The iterable
     */
    public StickyCollection(final Collection<E> list) {
        this.gate = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final List<E> temp = new LinkedList<>();
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
    public Iterator<E> iterator() {
        return this.gate.value().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.gate.value().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        return this.gate.value().toArray(array);
    }

    @Override
    public boolean add(final E element) {
        throw new UnsupportedOperationException(
            "#add(final E element) is not supported"
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
    public boolean addAll(final Collection<? extends E> collection) {
        throw new UnsupportedOperationException(
            "#addAll(final Collection<? extends T> collection) is not supported"
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
}
