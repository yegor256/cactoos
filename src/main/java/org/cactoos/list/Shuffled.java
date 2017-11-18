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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Shuffled list.
 *
 * <p>Pay attention that shuffling will happen on each operation
 * with the collection. Every time you touch it, it will fetch the
 * entire list from the encapsulated object and sort it. If you
 * want to avoid that "side-effect", decorate it with
 * {@link StickyList}.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.23
 * @see StickyList
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Shuffled<T> implements List<T> {

    /**
     * Sorted one.
     */
    private final UncheckedScalar<List<T>> scalar;

    /**
     * Ctor.
     * @param src The underlying collection
     */
    @SafeVarargs
    public Shuffled(final T... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying collection
     */
    public Shuffled(final Iterator<T> src) {
        this(() -> src);
    }

    /**
     * Ctor.
     * @param src The underlying collection
     */
    @SuppressWarnings("unchecked")
    public Shuffled(final Iterable<T> src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source
     */
    public Shuffled(final Collection<T> src) {
        this.scalar = new UncheckedScalar<>(
            () -> {
                final List<T> items = new LinkedList<>();
                items.addAll(src);
                Collections.shuffle(items);
                return Collections.unmodifiableList(items);
            }
        );
    }

    @Override
    public int size() {
        return this.scalar.value().size();
    }

    @Override
    public boolean isEmpty() {
        return this.scalar.value().isEmpty();
    }

    @Override
    public boolean contains(final Object item) {
        return this.scalar.value().contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return this.scalar.value().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.scalar.value().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return this.scalar.value().toArray(array);
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
    public boolean containsAll(final Collection<?> list) {
        return this.scalar.value().containsAll(list);
    }

    @Override
    public boolean addAll(final Collection<? extends T> list) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> item) {
        throw new UnsupportedOperationException("#addAll(index)");
    }

    @Override
    public boolean removeAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#removeAll()");
    }

    @Override
    public boolean retainAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#retainAll()");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("#clear()");
    }

    @Override
    public T get(final int index) {
        return this.scalar.value().get(index);
    }

    @Override
    public T set(final int index, final T element) {
        throw new UnsupportedOperationException("#set()");
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException("#add(index)");
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException("#remove(index)");
    }

    @Override
    public int indexOf(final Object item) {
        return this.scalar.value().indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        return this.scalar.value().lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.scalar.value().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return this.scalar.value().listIterator(index);
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return this.scalar.value().subList(start, end);
    }
}
