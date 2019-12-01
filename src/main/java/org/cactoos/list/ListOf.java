/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

/**
 * Iterable as {@link List}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link List} on each
 * method call. It doesn't cache the data anyhow. If you don't
 * need this {@link List} to re-fresh its content on every call,
 * by doing round-trips to the encapsulated iterable, decorate it with
 * {@link Sticky}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> List type
 * @see Sticky
 * @since 0.1
 */
public final class ListOf<T> implements List<T> {
    /**
     * List.
     */
    private final Unchecked<List<T>> list;

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
     * @param src An {@link Iterator}
     * @since 0.21
     */
    public ListOf(final Iterator<T> src) {
        this(() -> src);
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    public ListOf(final Iterable<T> src) {
        this(() -> {
            final List<T> temp = new LinkedList<>();
            src.forEach(temp::add);
            return Collections.unmodifiableList(temp);
        });
    }

    /**
     * Ctor.
     * @param slr The scalar
     */
    public ListOf(final Scalar<List<T>> slr) {
        this.list = new Unchecked<>(slr);
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.value().iterator();
    }

    @Override
    public boolean equals(final Object other) {
        return this.list.value().equals(other);
    }

    @Override
    public int hashCode() {
        return this.list.value().hashCode();
    }

    @Override
    public String toString() {
        return this.list.value().toString();
    }

    @Override
    public int size() {
        return this.list.value().size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.value().isEmpty();
    }

    @Override
    public boolean contains(final Object object) {
        return this.list.value().contains(object);
    }

    @Override
    public Object[] toArray() {
        return this.list.value().toArray();
    }

    @Override
    public <X> X[] toArray(final X[] array) {
        return this.list.value().toArray(array);
    }

    @Override
    public boolean add(final T item) {
        return this.list.value().add(item);
    }

    @Override
    public boolean remove(final Object object) {
        return this.list.value().remove(object);
    }

    @Override
    public boolean containsAll(final Collection<?> col) {
        return this.list.value().containsAll(col);
    }

    @Override
    public boolean addAll(final Collection<? extends T> col) {
        return this.list.value().addAll(col);
    }

    @Override
    public boolean removeAll(final Collection<?> col) {
        return this.list.value().removeAll(col);
    }

    @Override
    public boolean retainAll(final Collection<?> col) {
        return this.list.value().retainAll(col);
    }

    @Override
    public void clear() {
        this.list.value().clear();
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> col) {
        return this.list.value().addAll(index, col);
    }

    @Override
    public T get(final int index) {
        return this.list.value().get(index);
    }

    @Override
    public T set(final int index, final T element) {
        return this.list.value().set(index, element);
    }

    @Override
    public void add(final int index, final T element) {
        this.list.value().add(index, element);
    }

    @Override
    public T remove(final int index) {
        return this.list.value().remove(index);
    }

    @Override
    public int indexOf(final Object element) {
        return this.list.value().indexOf(element);
    }

    @Override
    public int lastIndexOf(final Object element) {
        return this.list.value().lastIndexOf(element);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list.value().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return this.list.value().listIterator(index);
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return this.list.value().subList(start, end);
    }
}
