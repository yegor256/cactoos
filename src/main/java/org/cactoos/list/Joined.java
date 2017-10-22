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
import java.util.stream.Collectors;

/**
 * Joined list.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @since 0.20
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Joined<X> implements List<X> {

    /**
     * The original lists.
     */
    private final List<List<X>> lists;

    /**
     * Ctor.
     * @param src Source lists
     */
    @SafeVarargs
    @SuppressWarnings("PMD.UseVarargs")
    public Joined(final List<X>... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source lists
     */
    public Joined(final List<List<X>> src) {
        this.lists = src;
    }

    @Override
    public int size() {
        int result = 0;
        for (final List<X> list : this.lists) {
            result += list.size();
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        boolean result = true;
        for (final List<X> list : this.lists) {
            if (!list.isEmpty()) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean contains(final Object obj) {
        boolean result = false;
        for (final List<X> list : this.lists) {
            if (list.contains(obj)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<X> iterator() {
        return this.joined().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.joined().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        return this.joined().toArray(array);
    }

    @Override
    public boolean add(final X item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        return this.joined().containsAll(collection);
    }

    @Override
    public boolean addAll(final Collection<? extends X> items) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends X> items) {
        throw new UnsupportedOperationException("#addAll(index)");
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException("#removeAll()");
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException("#retainAll()");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("#clear()");
    }

    @Override
    public X get(final int index) {
        return this.joined().get(index);
    }

    @Override
    public X set(final int index, final X element) {
        throw new UnsupportedOperationException("#set()");
    }

    @Override
    public void add(final int index, final X element) {
        throw new UnsupportedOperationException("#add(index)");
    }

    @Override
    public X remove(final int index) {
        throw new UnsupportedOperationException("#remove(index)");
    }

    @Override
    public int indexOf(final Object item) {
        throw new UnsupportedOperationException("#indexOf()");
    }

    @Override
    public int lastIndexOf(final Object item) {
        throw new UnsupportedOperationException("#lastIndexOf()");
    }

    @Override
    public ListIterator<X> listIterator() {
        throw new UnsupportedOperationException("#listIterator()");
    }

    @Override
    public ListIterator<X> listIterator(final int index) {
        throw new UnsupportedOperationException("#listIterator(index)");
    }

    @Override
    public List<X> subList(final int start, final int end) {
        return this.joined().subList(start, end);
    }

    /**
     * Joined list.
     *
     * @return Result list
     */
    private List<X> joined() {
        return this.lists.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
