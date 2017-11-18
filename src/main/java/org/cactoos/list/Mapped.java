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
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Mapped list.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.14
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Mapped<X, Y> implements List<Y> {

    /**
     * Original list.
     */
    private final List<X> list;

    /**
     * Function.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param src Source list
     * @param fnc Func
     * @since 0.21
     */
    public Mapped(final Iterator<X> src, final Func<X, Y> fnc) {
        this(new ListOf<>(src), fnc);
    }

    /**
     * Ctor.
     * @param src Source list
     * @param fnc Func
     */
    public Mapped(final Iterable<X> src, final Func<X, Y> fnc) {
        this(new ListOf<>(src), fnc);
    }

    /**
     * Ctor.
     * @param src Source list
     * @param fnc Func
     */
    public Mapped(final Collection<X> src, final Func<X, Y> fnc) {
        this(new ListOf<>(src), fnc);
    }

    /**
     * Ctor.
     * @param src Source list
     * @param fnc Func
     */
    public Mapped(final List<X> src, final Func<X, Y> fnc) {
        this.list = src;
        this.func = fnc;
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
    public boolean contains(final Object object) {
        throw new UnsupportedOperationException("#contains()");
    }

    @Override
    public Iterator<Y> iterator() {
        return new org.cactoos.iterator.Mapped<>(
            this.func, this.list.iterator()
        );
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("#toArray()");
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        throw new UnsupportedOperationException("#toArray(array)");
    }

    @Override
    public boolean add(final Y item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        throw new UnsupportedOperationException("#containsAll()");
    }

    @Override
    public boolean addAll(final Collection<? extends Y> items) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean addAll(final int index,
        final Collection<? extends Y> items) {
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
    public Y get(final int index) {
        return new UncheckedFunc<>(this.func).apply(this.list.get(index));
    }

    @Override
    public Y set(final int index, final Y element) {
        throw new UnsupportedOperationException("#set()");
    }

    @Override
    public void add(final int index, final Y element) {
        throw new UnsupportedOperationException("#add(index)");
    }

    @Override
    public Y remove(final int index) {
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
    public ListIterator<Y> listIterator() {
        throw new UnsupportedOperationException("#listIterator()");
    }

    @Override
    public ListIterator<Y> listIterator(final int index) {
        throw new UnsupportedOperationException("#listIterator(index)");
    }

    @Override
    public List<Y> subList(final int start, final int end) {
        return new Mapped<>(this.list.subList(start, end), this.func);
    }

}
